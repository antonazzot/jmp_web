{{/* Generate basic labels */}}
{{- define "mychart.labels" }}
  labels:
    current-date: {{ now | htmlDate }}
    version: {{ .Release.Name }}
{{- end }}