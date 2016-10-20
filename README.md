# GPix
GPix is an utility tool to download bulk images instantly.

## API
GPix has it's own public API hosted at https://gpix-shifz.rhcloud.com

#### How to use the API ?
**Step 1 - Obtain the API key.**

To get the API key, visit http://gpix-shifz.rhcloud.com/v1/get_api_key?email=[YOUR_EMAIL_ADDRESS]
replace [YOUR_EMAIL_ADDRESS] with your email address.

**Step 2 - Get the JSON**

Attach the API key with `Authorization` header and give a request to http://gpix-shifz.rhcloud.com/v1/gpix?keyword=[KEYWORD].

#### Example

To get the API key

GET - http://gpix-shifz.rhcloud.com/v1/get_api_key?email=theapache64@gmail.com

Result - 
```json
{
    "message": "API key sent to theapache64@gmail.com",
    "error": true,
    "error_code": 1
}
```

To get some car images
GET - http://gpix-shifz.rhcloud.com/v1/gpix?keyword=Car

```json
{
  "message": "100 images(s) available",
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

## CLI
Download the latest `jar` file from the release and run it from terminal/cmd.
`java -jar gpix.jar -help`
```
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
