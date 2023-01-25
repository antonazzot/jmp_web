docker build . -t epamantontsyrkunou/jmpdb
docker push epamantontsyrkunou/jmpdb
docker run -i -ti --rm --name jmpdb epamantontsyrkunou/jmpdb