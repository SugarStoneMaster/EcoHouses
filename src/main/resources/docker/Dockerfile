# Uso dell'immagine base di Java 17
FROM openjdk:17

# Copia del file WAR dalla cartella target nel container
COPY ./target/ecohouses-backend-1.0.0.war /usr/app/ecohouses-backend-1.0.0.war

# Impostazione della directory di lavoro
WORKDIR /usr/app

# Esecuzione del file WAR utilizzando Java
CMD ["java", "-jar", "ecohouses-backend-1.0.0.war"]