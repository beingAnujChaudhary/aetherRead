import os

directories = [
    r'd:\Projects\aetherRead',
    r'd:\Projects\portfolio-beingAnujChaudhary\src',
    r'd:\Projects\portfolio-beingAnujChaudhary\README.md'
]

extensions = {'.ts', '.tsx', '.md', '.json', '.css', '.html', '.js'}
exclude_dirs = {'.git', 'node_modules', '.next', 'out'}

def replace_in_file(filepath):
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
        
        if 'AetherRead' in content:
            new_content = content.replace('AetherRead', 'aetherRead')
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(new_content)
            print(f"Updated {filepath}")
    except Exception as e:
        pass

for path in directories:
    if os.path.isfile(path):
        replace_in_file(path)
    else:
        for root, dirs, files in os.walk(path):
            dirs[:] = [d for d in dirs if d not in exclude_dirs]
            for file in files:
                if any(file.endswith(ext) for ext in extensions):
                    replace_in_file(os.path.join(root, file))
