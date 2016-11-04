# GPix
GPix - An unofficial google images client to download bulk images with just a KEYWORD.

## API
GPix has it's own public API hosted at http://35.161.57.139:8080/gpix/v1

#### How to use the API ?
**Step 1 - Obtain the API key.**

To get the API key, visit http://35.161.57.139:8080/gpix/v1/get_api_key?email=[YOUR_EMAIL_ADDRESS]
replace [YOUR_EMAIL_ADDRESS] with your email address.

**Step 2 - Get the JSON**

Attach the API key with `Authorization` header and give a `GET` request to http://35.161.57.139:8080/gpix/v1/gpix?keyword=[KEYWORD].

#### API Usage example

To get the API key

GET - http://35.161.57.139:8080/gpix/v1/get_api_key?email=example@something.com

Result - 
```json
{
    "message": "API key sent to example@something.com",
    "error": false,
    "error_code": 0
}
```

To get some car images
GET - http://35.161.57.139:8080/gpix/v1/gpix?keyword=Car&limit=2

```json
{
  "message": "2 images(s) available",
  "error": false,
  "data": {
    "images": [
      {
        "height": 489,
        "image_url": "http://blog.caranddriver.com/wp-content/uploads/2015/11/BMW-2-series.jpg",
        "width": 800,
        "thumb_url": "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSaYHCVo5mY4cHGietbQfD96Am6gXcFTDZDT7Lz2cQ52mBWtCo69w"
      },
      {
        "height": 600,
        "image_url": "http://www.enterprise.com/content/dam/global-vehicle-images/cars/FORD_FOCU_2012-1.png",
        "width": 800,
        "thumb_url": "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSq94Ywt2zsMdUVum0XSb49oAYDMA-gmKy1eJVTqUD4j8yK_pSo"
      }
    ]
  },
  "error_code": 0
}
```

## Command line interface

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

|loveweakensthebrain|dump-fam-alone|
