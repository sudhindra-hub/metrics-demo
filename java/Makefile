.PHONY: package
package: 
	mvn -DskipTests clean package

.PHONY: docker
docker:
	docker build -t prometheus/metrics-demo:latest . -f Dockerfile
