# Cafeservices
Ini adalah services dari cafe

## Stack
Stack yang digunakan dalam mengembangkan aplikasi ini adalah sebagai berikut :
- Golang
- Postgres
- Docker
## Libs
Adapun library yang digunakan adalah :
| Name | Version |
| ---- | ---- |


## API Spesification

| URL | Method | Link | Type | Role |
| --- | --- | --- | --- | --- | 
| /cafe | GET | [Link](#[GET]%20/cafe) | public | Public |
| /cafe/:id | GET | [Link](#[GET]%20/cafe/:id) | public | Public |
| /cafe/query/search | POST | [Link](#[POST]%20/cafe/query/search) | public | Public |
| /cafe | POST | [Link](#[POST]%20/cafe) | public | Admin |
| /cafe/:id | PATCH | [Link](#[PATCH]%20/cafe/:id) | public | Admin |
| /cafe/update/self | PATCH | [Link](#[PATCH]%20/cafe/update/self) | public | Owner |
| /cafe/add/feed | PATCH | [Link](#[PATCH]%20/cafe/add/feed) | public | Owner |

### [GET] /cafe
Ini adalah route untuk menampilkan data cafe. Adapun `rule` yang digunakan adalah sebagai berikut :

Request Headers
```javascript
{}
```

Request Params
```javascript
base_url/cafe?limit=number&offset=number
```
require :
- `limit` must be number
- `offset` must be number

Request Body
```javascript 
{} 
```

Result Success
```javascript
{
    status : 200,
    data : [
        {
            _id : string, // id
            fk_auth: string, //owner
            c_name : string,
            c_profile : string, // link image
            c_address : {
                name : string // street,
                link : string // ling on gmaps
                location : {
                    provinsi : {
                        label : string,
                        value : number // id province
                    },
                    kota : {
                        label : string,
                        value : number // id kota
                    },
                    kecamatan : {
                        label : string,
                        value : number // id kecamatan
                    },
                    kelurahan : {
                        label : string,
                        value : number // id kelurahan
                    },
                }
            },
            c_category : string,
            c_contact : {
                name : string, // instagram
            },
            c_coordinate : [
                {x : float},
                {y : float},
            ],
            c_tags : [] // tags
        }
    ],
    limit : number,
    count : number // all data
}
```

Result Error
```javascript
{
    status : number, // 401, 403, 404, 500, 501
    error : string
}
```

[Back to Top](#API-Spesification)

### [GET] /cafe/:id
Ini adalah route untuk menampilkan detail cafe. Adapun `rule` yang digunakan adalah sebagai berikut :

Request Headers
```javascript
{}
```

Request Body
```javascript 
{} 
```

Result Success
```javascript
{
    status : 200,
    data : {
        c_name : string,
        c_profile : string,
        fk_user : {
            u_name : string, // from user services
        },
        c_contact : {
            phone : string,
            instagram : string, 
        }
        c_address : {
            province : string,
            city : string,
            poscode : string
        },
        c_coordinate : [
            {x : float},
            {y : float},
        ],
        c_category : string,
        c_detail : {
            features : string,
            description : string,
            schedule : [
                {
                    day : string, // mon, tue, wed, thu, fri, sat, sun
                    open : number,
                    close : number,
                    isClosed : boolean 
                }
            ],
            feeds : [
                {
                    time_upload : number,
                    pict : string, // pict url
                }
            ],
        },
        c_tags : [] // tags
    }
}
```

Result Error
```javascript
{
    status : number, // 401, 403, 404, 500, 501
    error : string
}
```

[Back to Top](#API-Spesification)

### [POST] /cafe/query/search
Ini adalah route untuk mencari data cafe. Adapun `rule` yang digunakan adalah sebagai berikut :

Request Headers
```javascript
{}
```

Request Body
```javascript 
{
    c_name : string,        // optional
    c_address : {
        province : string,  // diambil dari GPS, optional
        city : string       // diambil dari GPS, optional
    },
    c_category : string,    // optional
    c_tags : [],            // optional
} 
```

Request Params
```javascript
base_url/cafe?limit=number&offset=number
```
require :
- `limit` must be number
- `offset` must be number

Result Success
```javascript
{
    status : 200,
    data : [
        {
            c_name : string,
            c_profile : string,
            c_address : {
                province : string,
                city : string,
                poscode : string
            },
            c_coordinate : [
                {x : float},
                {y : float},
            ],
            c_tags : [] // tags
        }
    ],
    limit : number,
    count : number // all data
}
```

Result Error
```javascript
{
    status : number, // 401, 403, 404, 500, 501
    error : string
}
```
[Back to Top](#API-Spesification)

### [POST] /cafe

Ini adalah route untuk mencari data cafe. Adapun `rule` yang digunakan adalah sebagai berikut :

Request Headers
```javascript
{
    token : <token> 
}
```

Request Body
```javascript 
{
    c_name : string,        // optional
    c_profile : string,     // optional
    c_address : {
        province : string,  // optional
        city : string       // optional
        poscode : string 
    },
    c_category : string,    // optional
    c_coordinate : [
        {x : float},        // optional
        {y : float},        // optional
    ],
    c_detail : {
        features : string,  // optional
        description : string,   // optional
        schedule : [
            {
                day : string,   // mon, tue, wed, thu, fri, sat, sun
                open : number,  // optional
                close : number, // optional
                isClosed : boolean  // optional
            }
        ],
    },
    c_contact : {
        phone : string,     // optional
        instagram : string, // optional
    }
    c_tags : [],            // optional

} 
```

Result Success
```javascript
{
    status : 200,
    data : {
        c_name : string,
        c_profile : string,
        fk_user : {
            u_name : string, // from user services
        },
        c_contact : {
            phone : string,
            instagram : string, 
        }
        c_address : {
            province : string,
            city : string,
            poscode : string
        },
        c_coordinate : [
            {x : float},
            {y : float},
        ],
        c_category : string,
        c_detail : {
            features : string,
            description : string,
            schedule : [
                {
                    day : string, // mon, tue, wed, thu, fri, sat, sun
                    open : number,
                    close : number,
                    isClosed : boolean 
                }
            ],
            feeds : [], // empty array
        },
        c_tags : [] // tags
    }
}
```

Result Error
```javascript
{
    status : number, // 401, 403, 404, 500, 501
    error : string
}
```
[Back to Top](#API-Spesification)