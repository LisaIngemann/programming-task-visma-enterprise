# Programming-task-visma
The solution should be returned with full source

## Task
You are helping a small startup that wants to compete with IKEA. They need a simple system that keeps track of product assortment, customers and sold items. Since they want to compete with IKEA, they will sell furniture, textiles, and hot dogs.

A few prerequisites:

* Everything sold at the startup has a price and a description
* All items in the Furniture category have an item number and a weight (apart from price and description)
* All items in the Textile category have an item number and a color (apart from price and description) 
* Hot dogs have a flavor (apart from price and description), but no item number
* Customers can have a membership in the startup. Members get a discount on one ormore item categories.
    * Example 1: A customer buys a carpet and a chili-flavored hot dog. He is not a member, so he doesn’t get any discount.
    * Example 2: Another customer buys a wardrobe, a rug, and a vegetarian hot dog. She is a member, with furniture and hot dog discounts. So she gets a discount on the wardrobe and the hot dog, but pays normal price for the rug.

Items, categories and users can be inserted by a sql script or on application startup, no need to manage them

The application should expose REST for :
* create order
* get order - response should contain products and discounts

## Get all orders
GET localhost:8080/orders

## Create order
POST localhost:8080/orders - with raw JSON payload

Provide product item number for textiles and furniture, and flavor and quantity object for hot dogs.
```
{
  "userId": 1,
  "productItemNumbers": ["P001"],
  "hotDogs": [
    {
      "flavor": "Vegetarian",
      "quantity": 1
    },
    {
      "flavor": "Chilli",
      "quantity": 1
    }
  ]
}
```
If user is not registered, provide a user object.
Receives id, and a guest name if no name is specified.
```
{
  "user": {
    "name": "Roger",
    "membershipDiscounts": {
        "HOT_DOG": 0.05
    }
  },
  "productItemNumbers": ["P001", "P011"],
  "hotDogs": [
    {
      "flavor": "Chilli",
      "quantity": 1
    }
  ]
}
```

## In memory users, products and orders
### Users
```
{
  id: 1,
  name: "Lisa",
  membershipDiscounts: {
    FURNITURE: 0.15,
    HOT_DOG: 0.05
  }
},
{
  id: 2,
  name: "Monroe",
  membershipDiscounts: {
    TEXTILE: 0.20
  }
},
{
  id: 3,
  name: "Tor",
  membershipDiscounts: {}
}
```
### Products
```
{
  itemNumber: "P001",
  category: FURNITURE,
  weight: 10.0,
  price: 500.0,
  color: "",
  flavor: null,
  description: "Solid wooden chair"
},
{
  itemNumber: "P003",
  category: FURNITURE,
  weight: 1.2,
  price: 1250.0,
  color: "",
  flavor: null,
  description: "Wardrobe for hallway"
},
{
  itemNumber: "P005",
  category: TEXTILE,
  weight: 0.0,
  price: 150.0,
  color: "Red",
  flavor: null,
  description: "Wooly rug"
},
{
  itemNumber: "P011",
  category: TEXTILE,
  weight: 0.0,
  price: 550.0,
  color: "Blue",
  flavor: null,
  description: "Premium seagrass carpet"
},
{
  itemNumber: null,
  category: HOT_DOG,
  weight: 0.0,
  price: 10.0,
  color: null,
  flavor: "Chilli",
  description: "Classic chilli-flavored hot dog"
},
{
  itemNumber: null,
  category: HOT_DOG,
  weight: 0.0,
  price: 10.0,
  color: null,
  flavor: "Vegetarian",
  description: "Mild vegetarian hot dog"
}
```
### Orders
```
{
  "id": 1,
  "user": {
    "id": 1,
    "name": "Lisa",
    "membershipDiscounts": {
      "FURNITURE": 0.15,
      "HOT_DOG": 0.05
    }
  },
  "products": [
    {
      "itemNumber": "P003",
      "category": "FURNITURE",
      "weight": 1.2,
      "price": 1250.0,
      "color": "",
      "flavor": null,
      "description": "Wardrobe for hallway"
    },
    {
      "itemNumber": "P005",
      "category": "TEXTILE",
      "weight": 0.0,
      "price": 150.0,
      "color": "Red",
      "flavor": null,
      "description": "Wooly rug"
    },
    {
      "itemNumber": null,
      "category": "HOT_DOG",
      "weight": 0.0,
      "price": 10.0,
      "color": null,
      "flavor": "Vegetarian",
      "description": "Mild vegetarian hot dog"
    }
  ],
  "totalPrice": 1222.0
},
{
  "id": 2,
  "user": {
    "id": 3,
    "name": "Tor",
    "membershipDiscounts": {}
  },
  "products": [
    {
      "itemNumber": "P011",
      "category": "TEXTILE",
      "weight": 0.0,
      "price": 550.0,
      "color": "Blue",
      "flavor": null,
      "description": "Premium seagrass carpet"
    },
    {
      "itemNumber": null,
      "category": "HOT_DOG",
      "weight": 0.0,
      "price": 10.0,
      "color": null,
      "flavor": "Chilli",
      "description": "Classic chilli-flavored hot dog"
    }
  ],
  "totalPrice": 560.0
}
```
