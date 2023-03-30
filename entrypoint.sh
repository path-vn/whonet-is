#!/bin/bash

printenv | grep IS

java ${JAVA_OPTS:-'-Xmx1g'} \
    -Dspring.servlet.multipart.max-file-size=${IS_MAX_FILE_SIZE:-'50MB'} \
    -Dspring.servlet.multipart.max-request-size=${IS_MAX_REQUEST_SIZE:-'100MB'} \
    -Dspring.profiles.active=${IS_SPRING_PROFILES:-'prod,api-docs'} \
    -Dspring.datasource.username=${IS_DATABASE_USERNAME:-'iis'} \
    -Dspring.datasource.password=${IS_DATABASE_PASSWORD:-'iis@123'} \
    -Dspring.liquibase.enabled=${IS_LIQUIBASE_ENABLE:-'false'} \
    -Dwhonet.thread=${IS_WHONET_THREAD:-'10'} \
    -Dwhonet.priority=${IS_WHONET_PRIORITY:-'Meningitis,Non-meningitis,Parenteral,Oral,,Urine,Intravenous,Extraintestinal'} \
    -Dapplication.baseDirectory=${IS_BASE_DIRECTORY:-'/opt/data'} \
    -Dspring.datasource.url=${IS_DATABASE:-'jdbc:mysql://localhost:3306/iis?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true'} \
    -Dspring.data.rest.max-page-size=${REST_MAX_PAGE_SIZE:-'5000000'} \
    -Dspring.data.web.pageable.max-page-size=${REST_MAX_PAGE_SIZE:-'5000000'} \
    -Dserver.port=${IS_PORT:-'8080'} \
    -jar /app.jar ${0} ${@}
