#mvn clean package
#docker build . -t epamantontsyrkunou/jmp-1.0.0
#docker push epamantontsyrkunou/jmp-1.0.0
docker run -i -ti -p 8989:8989 \
--env-file ./kustom.env \
--name jmp epamantontsyrkunou/jmp-1.0.0
