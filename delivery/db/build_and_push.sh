#docker build . -t epamantontsyrkunou/jmpdb
#docker push epamantontsyrkunou/jmpdb
docker run -i -ti -p 5433:5432 --rm --name jmpdb epamantontsyrkunou/jmpdb