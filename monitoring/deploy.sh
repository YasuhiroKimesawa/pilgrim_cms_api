#!/usr/bin/env bash

cwd=`dirname "${0}"`
deploy_bucket='pilgrim-crm-monitoring-app-deployment'

mkdir -p ./dist
rm -rf ./dist/*
cp -r ${cwd}/app ${cwd}/dist/app
cp ${cwd}/template.yml ${cwd}/dist/template.yml
pip install -r requirements.txt -t ./dist

if aws s3 ls s3://${deploy_bucket} --region ap-northeast-1 --profile ${AWS_PROFILE} 2>&1 | grep -q 'NoSuchBucket'; then
    aws s3 mb s3://${deploy_bucket} --region ap-northeast-1 --profile ${AWS_PROFILE}
fi

aws cloudformation package \
    --template-file ${cwd}/dist/template.yml \
    --s3-bucket ${deploy_bucket} \
    --output-template-file ${cwd}/dist/packaged-template.yml \
    --profile ${AWS_PROFILE}

aws cloudformation deploy \
    --template-file ${cwd}/dist/packaged-template.yml \
    --stack-name pilgrim-crm-monitoring \
    --capabilities CAPABILITY_IAM \
    --profile ${AWS_PROFILE} \
    --parameter-overrides StageName=dev SlackWebhookURL=${SLACK_WEBHOOK_URL} HealthCheckURL=${HEALTH_CHECK_URL}
