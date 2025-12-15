docker-compose up -d
This brings up Grafana and Prometheus

docker-compose down

Grafana (http://localhost:3000)

Navigate to http://localhost:3000
Login: admin / admin
Add Data Source:

Click "Add your first data source"
Select "Prometheus"
URL: http://prometheus:9090
Click "Save & Test"


Create Dashboard:

Click "+" → "Dashboard" → "Add visualization"
Select "Prometheus" data source
Add panels with these queries: