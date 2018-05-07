# -*- coding:utf-8 -*-
import urllib.parse
from xml.etree import ElementTree as ET

import boto3 as boto3


def notify_coverage_handler(event, context):
    """
    S3からキックされ、カバレッジ結果を通知する

    s3_bucket_name = event['Records'][0]['s3']['bucket']['name']
    s3_key = urllib.parse.unquote_plus(
        event['Records'][0]['s3']['object']['key'], encoding='utf-8')
    s3 = boto3.resource('s3')
    s3_object = s3.Object(s3_bucket_name, s3_key)
    result = s3_object.get()
    xml_string = result['Body'].read()

    root = ET.fromstring(xml_string)
    root.get("")
    """
    pass


