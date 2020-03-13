#!/usr/bin/env python
# -*- encoding: utf-8 -*-
# Created on 2020-03-05 08:31:11
# Project: Question

from pyspider.libs.base_handler import *
import random
import bs4
import pymysql as mysql

flag=0
class Handler(BaseHandler):
crawl_config = {


}
@every(minutes=24 * 60)
def on_start(self):
self.crawl('https://www.shixiseng.com/interns?page=1&keyword=%E5%90%8E%E7%AB%AF%E5%BC%80%E5%8F%91%E5%AE%9E%E4%B9%A0%E7%94%9F&type=intern&area=&months=&days=&degree=&official=&enterprise=&salary=-0&publishTime=&sortType=&city=%E5%85%A8%E5%9B%BD&internExtend=', callback=self.addpages)

#爬取的数据加入到数据库里面
def intoDatabase(self,title,content):
db = mysql.connect(host='127.0.0.1', port=3308, user='root', password='zc19970919',
db='practice', charset='utf8')
try:
cursor = db.cursor()
sql = 'insert into question(title, content, user_id, created_date,comment_count) values ("%s","%s",%d, %s, %d)' % (title,content, random.randint(1, 10), 'now()', 0);
print(sql)
cursor.execute(sql)
qid = cursor.lastrowid
print (qid)
db.commit()
except Exception as e:
print (e)
db.rollback()
db.close()



#添加分页10 * 24 * 60 * 60
@config(age=10 * 24 * 60 * 60)
def addpages(self,response):
pagetotal=0
for each in response.doc('.number').items():
# global pagetotal
pagetotal=each.text()
for each in range(1,int(pagetotal)):
self.crawl("https://www.shixiseng.com/interns?page={}&keyword=Java&type=intern&area=&months=&days=&degree=&official=&enterprise=&salary=-0&publishTime=&sortType=&city=%E5%85%A8%E5%9B%BD&internExtend=".format(each),callback=self.index_page)


@config(age=10 * 24 * 60 * 60)
def index_page(self, response):
'''
global flag
if(flag==0):
self.addpages(response)
flag=flag+1
'''
for each in response.doc('div.f-l.intern-detail__job>p>a').items():
self.crawl(each.attr.href, callback=self.detail_page)


@config(priority=2)
def detail_page(self, response):
com=response.doc('div.com_intro>a.com-name ').text()
comDesc=response.doc('div.com_intro>div.com-desc ').text()
'''
for each in response.doc('div.com-detail>div>i.iconfont.iconhangyelingyu').items():
comDetail=comDetail+each.text()+'  '
'''
comDetailhtml=response.doc('div.com-detail').html()

ba4forcomdetail=bs4.BeautifulSoup(comDetailhtml)
comDetail=ba4forcomdetail.get_text()

jobName=response.doc('div.new_job_name>span').text()
html=response.doc('div.job_part>div.job_detail').html()
osobject=bs4.BeautifulSoup(html)
jobRequrement=osobject.get_text()
jobName=jobName.replace('"','/"')
jobRequrement=jobRequrement.replace('"','\"')
self.intoDatabase(jobName,jobRequrement)
return {
"com":com,
"comDesc":comDesc,
"comDetail":comDetail,
'jobName':jobName,
'jobRequrement':jobRequrement
}
