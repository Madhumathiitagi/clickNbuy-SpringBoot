INSERT INTO product (name, price, stock, description, category, image_link, created_time) VALUES 
('iPhone 15 Pro', 129900.0, 5, 'Apple iPhone 15 Pro with Titanium design and A17 Pro chip.', 'Electronics', 'https://images.unsplash.com/photo-1695048133142-1a20484d2569?auto=format&fit=crop&q=80&w=400', NOW()),
('MacBook Air M2', 114900.0, 3, 'Apple MacBook Air with M2 chip, 13.6-inch Liquid Retina display.', 'Electronics', 'https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?auto=format&fit=crop&q=80&w=400', NOW()),
('Sony WH-1000XM5', 29990.0, 10, 'Industry leading noise canceling headphones with 30 hours battery life.', 'Accessories', 'https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?auto=format&fit=crop&q=80&w=400', NOW()),
('Samsung Galaxy S24', 79999.0, 7, 'Galaxy S24 with AI features and stunning camera.', 'Electronics', 'https://images.unsplash.com/photo-1678911820864-e2c567c655d7?auto=format&fit=crop&q=80&w=400', NOW()),
('Nike Air Max 270', 12995.0, 15, 'Comfortable and stylish Nike Air Max sneakers.', 'Footwear', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&q=80&w=400', NOW()),
('Adidas Ultraboost', 17999.0, 12, 'Responsive cushioning for a smooth, energized ride.', 'Footwear', 'https://images.unsplash.com/photo-1587563871167-1ee9c731aefb?auto=format&fit=crop&q=80&w=400', NOW()),
('Kindle Paperwhite', 13999.0, 20, 'Now with a 6.8 inch display and thinner borders.', 'Electronics', 'https://images.unsplash.com/photo-1594980596870-8aa52a78d8cd?auto=format&fit=crop&q=80&w=400', NOW()),
('Fossil Gen 6', 24995.0, 8, 'The latest smart watch from Fossil with faster charging.', 'Accessories', 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&q=80&w=400', NOW()),
('Ray-Ban Wayfarer', 9990.0, 25, 'Classic style that never goes out of fashion.', 'Accessories', 'https://images.unsplash.com/photo-1572635196237-14b3f281503f?auto=format&fit=crop&q=80&w=400', NOW()),
('Levis 501 Original', 4599.0, 30, 'The original button fly jean first created in 1873.', 'Clothing', 'https://images.unsplash.com/photo-1542272604-787c3835535d?auto=format&fit=crop&q=80&w=400', NOW()),
('ZARA Leather Jacket', 8999.0, 5, 'Classic biker jacket in high quality leather.', 'Clothing', 'https://images.unsplash.com/photo-1551028719-00167b16eac5?auto=format&fit=crop&q=80&w=400', NOW()),
('Herschel Backpack', 5499.0, 18, 'Timeless design with modern functionality.', 'Accessories', 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?auto=format&fit=crop&q=80&w=400', NOW());

INSERT INTO users (name, email, password, mobile, gender, otp, verified, role, created_time) VALUES 
('Admin User', 'admin@clicknbuy.com', '$2a$10$W4QMlcKEWGSjsllQ4p3EZ.B6C3SeJQec11B0n.bEfv81.688/Yism', 9999999999, 'Male', 0, true, 'ROLE_ADMIN', NOW()),
('Demo User', 'user@example.com', '$2a$10$W4QMlcKEWGSjsllQ4p3EZ.B6C3SeJQec11B0n.bEfv81.688/Yism', 8888888888, 'Female', 0, true, 'ROLE_USER', NOW());
