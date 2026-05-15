import os
import re

def find_unused_imports(directory):
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):
                filepath = os.path.join(root, file)
                with open(filepath, 'r', encoding='utf-8') as f:
                    lines = f.readlines()
                
                imports = []
                content = ""
                for line in lines:
                    if line.startswith("import "):
                        imports.append(line.strip())
                    else:
                        content += line
                
                for imp in imports:
                    # Extract the class name
                    class_name = imp.split(".")[-1].replace(";", "")
                    if class_name == "*":
                        continue
                    
                    # Check if class_name is used in content
                    # Use regex to find whole word matches
                    if not re.search(r'\b' + re.escape(class_name) + r'\b', content):
                        print(f"Unused import: {imp} in {filepath}")

find_unused_imports("c:\\Users\\HP\\Downloads\\clickNbuy-SpringBoot-Thymeleaf-master\\clickNbuy-SpringBoot-Thymeleaf-master\\src\\main\\java")
