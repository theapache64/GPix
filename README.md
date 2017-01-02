# GPix
GPix - An unofficial Google Images API.

## API
API-end-point: http://theapache64.xyz:8080/gpix/v1

#### How to use the API ?
**Step 1 - Obtain the API key.**

To get the API key, visit http://theapache64.xyz:8080/gpix/v1/get_api_key?email=[YOUR_EMAIL_ADDRESS]
where [YOUR_EMAIL_ADDRESS] is your email address.

Then we'll send your API key to the given email.

**Step 2 -Finally, Get the result JSON**

Attach the API key either with `Authorization` header or request parameter `Authorization` and give a `GET` or `POST` request to http://theapache64.xyz:8080/gpix/v1/gpix?keyword=[KEYWORD].
where [KEYWORD] is the keyword you want to search, for example : Car, Jacob Black etc.

#### API Usage example

To get the API key

`GET` - http://theapache64.xyz:8080/gpix/v1/get_api_key?email=[YOUR-EMAIL-ADDRESS-GOES-HERE]

Result - 
```json
{
    "message": "API key sent to your-email-address",
    "error": false,
    "error_code": 0
}
```

To get 50 images of Audi. (here am using the test API key with the GET request)
GET - http://theapache64.xyz:8080/gpix/v1/gpix?Authorization=testApiKey&keyword=Audi&limit=50

```json
{
  "message": "50 images(s) available",
  "error": false,
  "data": {
    "images": [
      {
        "height": 489,
        "image_url": "http://blog.caranddriver.com/wp-content/uploads/2015/11/BMW-2-series.jpg",
        "width": 800,
        "thumb_url": "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSaYHCVo5mY4cHGietbQfD96Am6gXcFTDZDT7Lz2cQ52mBWtCo69w"
      },
      ...
    ]
  },
  "error_code": 0
}
```

NOTE: Do not use the `testApiKey` for commercial purposes. It may change in future.

## Command line interface

We've also a command line application written in `Java` to download bulk images.

Download the latest `jar` file from the release and run it from terminal/cmd.
`java -jar gpix.jar -help`
```
to get the help text.

usage: gpix [-b] [-d <arg>] [-f] [-help] [-k <arg>] [-n <arg>] [-o] [-t]
       [-z]
To download bulk images
 -b         Download  both thumbnail and image
 -d <arg>   Directory to parse the data. If not specified, current
            directory will be used.
 -f         To keep original file name (coming soon)
 -help      To display this text
 -k <arg>   Keyword
 -n <arg>   Number of images to be downloaded.
 -o         Download original only
 -t         Download thumbnail only
 -z         To get output as zipped file (coming soon)
 
Please shoot issues to theapache64@gmail.com
```
## GUI
(coming soon)

## Android Application
(coming soon)

## TODOs
- Make a status page to display users , the number of hits and last hit query.

## Poor documentation!
If you feel the documentation is poor or you still don't know how to use the GPix API, please shoot me your query to theapache64@gmail.com :). Happy to help.

#Bugs ?
Report an issue or shoot me a mail to theapache64@gmail.com .

|loveweakensthebrain|dump-fam-alone|xam-network|like-v2|
