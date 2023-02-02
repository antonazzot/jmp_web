version="jmp-1.0.8"

#mvn clean package
#docker build . -t epamantontsyrkunou/$version
#docker push epamantontsyrkunou/$version
#helm upgrade jmpapp --version $version ./delivery/mainchart
#kubectl delete -A ValidatingWebhookConfiguration ingress-nginx-admission
docker run -i -ti --rm -p 8989:8989 \
--env-file ./kustom.env \
--name jmp epamantontsyrkunou/$version
