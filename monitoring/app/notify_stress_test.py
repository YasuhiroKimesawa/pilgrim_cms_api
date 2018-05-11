# -*- coding:utf-8 -*-
import json

import os
from urllib import request
from urllib import parse

import boto3 as boto3
from boto3 import Session


def notify_stress_test_handler(event, context):
    """
    S3からキックされ、負荷テスト結果を通知する
    """
    # 環境変数
    web_site_url = os.environ['WebSiteBaseURL']
    slack_post_url = os.environ['SLACK_POST_URL']

    # event
    s3_bucket_name = event['Records'][0]['s3']['bucket']['name']
    print("s3_bucket_name:" + s3_bucket_name)

    post(build_message(s3_bucket_name, web_site_url), slack_post_url)
    return


def build_message(s3_bucket_name, web_site_url):
    """
    Slack用のメッセージを作成
    """
    post_message = '負荷テストが完了しました \n'
    s3client = boto3.client('s3')
    list_objects = s3client.list_objects_v2(Bucket=s3_bucket_name)
    for folder in list_objects["Contents"]:
        if folder["Key"][-10:] != "index.html":
            continue

        post_message = post_message + (web_site_url + "/" + folder["Key"] + "\n")

    side_color = "good"

    post_data = {'attachments': [{'color': side_color, 'text': post_message}]}

    return post_data


def post(payload, slack_post_url):
    """
    SlackにメッセージをPOSTする
    """
    payload_json = json.dumps(payload)
    data = parse.urlencode({"payload": payload_json})
    req = request.Request(slack_post_url)
    response = request.build_opener(request.HTTPHandler()).open(req, data.encode('utf-8'))
    return response.read().decode('utf-8')
