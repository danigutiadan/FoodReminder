import os

# Función para crear directorios
def create_dirs(dirs):
    for dir in dirs:
        if not os.path.exists(dir):
            os.makedirs(dir)

# Nombre del módulo
module_name = input("Nombre del módulo: ")
# Ruta donde se debe crear el módulo
module_path = input("Ruta donde crear el módulo: ")

# Crear la estructura de carpetas
base_dir = module_path
module_dir = os.path.join(base_dir, module_name)
domain_dir = os.path.join(module_dir, "domain")
data_dir = os.path.join(module_dir, "data")
ui_dir = os.path.join(module_dir, "ui")
domain_packages = ["models", "repository", "usecases"]
data_packages = ["datasource", "local", "remote", "repository"]
ui_packages = ["screens", "components"]
domain_dirs = [os.path.join(domain_dir, package) for package in domain_packages]
data_dirs = [os.path.join(data_dir, package) for package in data_packages]
ui_dirs = [os.path.join(ui_dir, package) for package in ui_packages]
dirs = [module_dir, domain_dir, data_dir, ui_dir] + domain_dirs + data_dirs + ui_dirs
create_dirs(dirs)

# Imprimir la estructura de carpetas creada
print("Estructura de carpetas creada:")
print(f"- {module_dir}")
print(f"  - {domain_dir}")
for dir in domain_dirs:
    print(f"    - {dir}")
print(f"  - {data_dir}")
for dir in data_dirs:
    print(f"    - {dir}")
print(f"  - {ui_dir}")
for dir in ui_dirs:
    print(f"    - {dir}")
