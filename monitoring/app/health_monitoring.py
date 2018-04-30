# -*- coding:utf-8 -*-
import json

import os
from urllib import request
from urllib import parse

import requests


def health_monitoring_handler(event, context):
    """
    Eventからキックされ、サーバーのHealthチェックを行い通知する
    """
    # 環境変数
    url = os.environ['CHECK_URL']
    slack_post_url = os.environ['SLACK_POST_URL']

    response = requests.get(url)

    if response.status_code != 200:
        post(build_failure_message(url), slack_post_url)
        return

    post(build_success_message(url), slack_post_url)


def build_failure_message(url):
    """
    Slack用のメッセージを作成
    """
    side_color = "danger"

    post_message = '[' + url + '] に接続できません'
    post_data = {'attachments': [{'color': side_color, 'text': post_message}]}

    return post_data


def build_success_message(url):
    """
    Slack用のメッセージを作成
    """
    side_color = "good"

    post_message = '[' + url + '] に接続できました'
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
