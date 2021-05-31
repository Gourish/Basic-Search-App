# Basic-Search-App
Search Engine to retrieve documents based on the key word provided. 
## Steps

1. [install elastic search](https://www.itzgeek.com/how-tos/linux/ubuntu-how-tos/install-elasticsearch-on-centos-7-ubuntu-14-10-linux-mint-17-1.html)
2. [setup google credentials to communicate with google](https://developers.google.com/identity/protocols/oauth2)

### Sample API requests and responses 

1. GET : http://localhost:8081/data/download
 
 The above API will ask for application to download files in a specific folder from google drive and parse it and store it as document in elasticsearch.
 If the task is successfully executed then HTTP 200 code will be returned .In case of some error Error message is returned along with apropriate HTTP response.
 
 2. GET: http://localhost:8081/search/{keyword}
    Ex: http://localhost:8081/search/task        where task is a keyword based on which searching of documents will take place 
    
 Above api will request the documents with specified keyword. sample response will be list of all the file names and URL of the file in Gdrive where keyword has
 been found . 
   
