#!/usr/bin/env bash

cwd=$(pwd)/monitoring
deploy_bucket='pilgrim-crm-monitoring-app-deployment'

mkdir -p ${cwd}/dist
rm -rf ${cwd}/dist/*
cp -r ${cwd}/app ${cwd}/dist/app
cp ${cwd}/template.yml ${cwd}/dist/template.yml

if aws s3 ls s3://${deploy_bucket} --region ap-northeast-1 --profile ${AWS_PROFILE} 2>&1 | grep -q 'NoSuchBucket'; then
    aws s3 mb s3://${deploy_bucket} --region ap-northeast-1 --profile ${AWS_PROFILE}
fi

(cd ${cwd};pip install -r requirements.txt -t ${cwd}/dist)

(cd ${cwd};aws cloudformation package \
    --template-file ${cwd}/dist/template.yml \
    --s3-bucket ${deploy_bucket} \
    --output-template-file ${cwd}/dist/packaged-template.yml \
    --profile ${AWS_PROFILE})

(cd ${cwd};aws cloudformation deploy \
    --template-file ${cwd}/dist/packaged-template.yml \
    --stack-name pilgrim-crm-monitoring \
    --capabilities CAPABILITY_IAM \
    --profile ${AWS_PROFILE} \
    --parameter-overrides StageName=dev  \
    SlackWebhookURL=${SLACK_WEBHOOK_URL}  \
    HealthCheckURL=${HEALTH_CHECK_URL} \
    StressTestResultAllowIPHome=${STRESS_TEST_RESULT_ALLOW_IP_HOME} )

