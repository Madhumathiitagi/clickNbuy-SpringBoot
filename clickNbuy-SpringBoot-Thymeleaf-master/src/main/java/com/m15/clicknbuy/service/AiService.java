package com.m15.clicknbuy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m15.clicknbuy.entity.Product;
import com.m15.clicknbuy.repository.ProductRepository;

import java.util.Map;

@Service
public class AiService {

    private static final Logger logger = LoggerFactory.getLogger(AiService.class);

    private final ChatClient chatClient;

    @Autowired
    private ProductRepository productRepository;

    public AiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String getAiResponse(String userMessage) {
        // Fetch products to give context to the AI
        List<Product> products = productRepository.findAll();
        logger.info("Found {} products for context", products.size());
        String productContext = products.stream()
                .map(p -> String.format("- ID: %d, Name: %s (Category: %s, Price: ₹%s): %s", 
                        p.getId(), p.getName(), p.getCategory(), p.getPrice(), p.getDescription()))
                .collect(Collectors.joining("\n"));

        String systemPrompt = """
                You are the AI Shopping Assistant for ClickNBuy, a premium e-commerce store.
                Your goal is to help users find products, answer questions about our catalog, and provide a great shopping experience.
                
                Our current product catalog:
                {productContext}
                
                If a user asks for something we don't have, politely suggest the closest alternative or tell them we'll be adding more products soon.
                Be friendly, professional, and concise. Use emojis occasionally to be engaging.
                
                CRITICAL INSTRUCTION: When recommending a product, ALWAYS include a "Buy" button using this EXACT HTML format:
                <a href="/user/buy-now/{ID}" class="btn btn-sm btn-success mt-2">Buy Now</a>
                Replace {ID} with the product's ID.
                """;

        try {
            PromptTemplate template = new PromptTemplate(systemPrompt);
            String finalPrompt = template.render(Map.of("productContext", productContext));

            return chatClient.prompt()
                    .system(finalPrompt)
                    .user(userMessage)
                    .call()
                    .content();
        } catch (Exception e) {
            logger.error("AI API failed, using local fallback: ", e);
            return getLocalFallbackResponse(userMessage, products);
        }
    }

    private String getLocalFallbackResponse(String userMessage, List<Product> products) {
        String query = userMessage.toLowerCase();
        
        // Simple keyword matching for products
        List<Product> matches = products.stream()
                .filter(p -> query.contains(p.getName().toLowerCase()) || 
                             query.contains(p.getCategory().toLowerCase()))
                .collect(Collectors.toList());

        if (!matches.isEmpty()) {
            StringBuilder response = new StringBuilder("I found some products you might like! 😊\n\n");
            for (Product p : matches) {
                response.append(String.format("🛍️ **%s** - ₹%s\n", p.getName(), p.getPrice()));
                response.append(String.format("   *%s*\n", p.getDescription()));
                response.append(String.format("   <a href='/user/buy-now/%d' class='btn btn-sm btn-success mt-2'>Buy Now</a>\n\n", p.getId()));
            }
            response.append("Would you like more details on any of these?");
            return response.toString();
        }

        if (query.contains("hi") || query.contains("hello") || query.contains("hey")) {
            return "Hello! I'm your ClickNBuy assistant. How can I help you find the perfect product today? 🛍️";
        }

        if (query.contains("help") || query.contains("support")) {
            return "I can help you search for products in our catalog. Just tell me what you're looking for, like 'laptop' or 'shoes'!";
        }

        return "I'm currently operating in offline mode, but I can still help you find products! We have a great selection of " + 
               products.stream().map(Product::getCategory).distinct().limit(3).collect(Collectors.joining(", ")) + 
               ". What are you looking for? 😊";
    }

    public List<Product> searchProducts(String query) {
        try {
            // Use AI to extract keywords or category from natural language query
            String extractionPrompt = String.format("""
                    Extract the main product category or keyword from this user search query: "%s".
                    Only return the keyword or category name, nothing else.
                    """, query);
            
            String keyword = chatClient.prompt()
                    .user(extractionPrompt)
                    .call()
                    .content()
                    .trim();

            // Search using the extracted keyword
            return productRepository.findAll().stream()
                    .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()) || 
                                 p.getCategory().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Search AI failed, using simple fallback for query: " + query);
            // Simple fallback search using the raw query
            return productRepository.findAll().stream()
                    .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()) || 
                                 p.getCategory().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }
}
