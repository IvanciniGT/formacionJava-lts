
def saluda(nombre):
    print("Hola, " + nombre)

saluda("Menchu")

variable = saluda               # Qué hago aquí? Referencio la función saluda desde la variable variable
print(variable)

variable("Ivan")                # Qué hago aquí? Llamo a la función saluda a través de la variable variable

def generar_saludo_formal(nombre):
    return "Estimado/a " + nombre + ","

def generar_saludo_informal(nombre):
    return "Hola, " + nombre

def imprimir_saludo(funcion_generadora_de_saludos, nombre):
    print(funcion_generadora_de_saludos(nombre))

imprimir_saludo(generar_saludo_formal, "Menchu")
imprimir_saludo(generar_saludo_informal, "Menchu")


# Que permite esto? Definir una función, en la que PARTE DE LA LOGICA se suministra como argumento.