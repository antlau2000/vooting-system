# Restaurant voting

#### Register

```curl -s -X POST -d '{"name":"newName","email":"newemail@ya.ru","password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting_system/profile/register```

Produces:
```{"id":100043,"name":"newName","email":"newemail@ya.ru","enabled":true,"registered":"2020-10-26T16:54:23.802+00:00","roles":["USER"]}```

#### Get actual list of restaurants (list consists of transfer objects and is sorted by daily rating)

```curl -s 'http://localhost:8080/voting_system/restaurants'```

Produces
```[{"id":100002,"name":"Corner Grill","rating":1},{"id":100003,"name":"Bluefin","rating":0},{"id":100005,"name":"Caesars Palace","rating":0},{"id":100004,"name":"McDonalds","rating":0}]```

### Authorized user
#### Get user profile

```curl -s  'http://localhost:8080/voting_system/profile' --user admin@gmail.com:admin```

Produces
```{"id":100001,"name":"Admin","email":"admin@gmail.com","enabled":true,"registered":"2020-10-26T16:35:35.037+00:00","roles":["ADMIN"],"votes":null}```

#### Get user profile with votes

```curl -s  'http://localhost:8080/voting_system/profile/with-votes' --user admin@gmail.com:admin```

#### Get votes

```curl -s  'http://localhost:8080/voting_system/profile/votes/' --user admin@gmail.com:admin```

Produces
```[{"id":100012,"localDate":"2020-11-16"},{"id":100011,"localDate":"2020-08-12"},{"id":100010,"localDate":"2020-08-11"},{"id":100009,"localDate":"2020-08-10"}]```

#### Get today's vote

```curl -s  'http://localhost:8080/voting_system/profile/votes/today' --user admin@gmail.com:admin```

Produces
```{"id":100012,"localDate":"2020-11-16"}```

#### Get vote by date

```curl -s  'http://localhost:8080/voting_system/profile/votes/by?date=2020-08-11' --user admin@gmail.com:admin```

Produces
```{"id":100010,"localDate":"2020-08-11"}```

#### Get today's vote

```curl -s  'http://localhost:8080/voting_system/profile/votes' --user admin@gmail.com:admin```

Produces
```{"id":100001,"name":"Admin","email":"admin@gmail.com","enabled":true,"registered":"2020-10-26T16:35:35.037+00:00","roles":["ADMIN"],"votes":[{"id":100011,"date":"2020-08-12"},{"id":100010,"date":"2020-08-11"},{"id":100009,"date":"2020-08-10"}]}```

#### Update user (user updates himself)

```curl -s -X PUT -d '{"name":"newName","email":"newemail@ya.ru","password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting_system/profile/ --user admin@gmail.com:admin```

Produces
```{"id":100001,"name":"newName","email":"newemail@ya.ru","enabled":true,"registered":"2020-10-26T16:58:31.488+00:00","roles":["ADMIN"],"votes":null}```

#### Delete user (user deletes himself)

```curl -s -X DELETE  'http://localhost:8080/voting_system/profile' --user admin@gmail.com:admin```

#### Get list of restaurants by date

```curl -s 'http://localhost:8080/voting_system/restaurants/by?date=2020-08-10' --user admin@gmail.com:admin```

Produces
```[{"id":100003,"name":"Bluefin","rating":1},{"id":100002,"name":"Corner Grill","rating":1},{"id":100005,"name":"Caesars Palace","rating":0},{"id":100004,"name":"McDonalds","rating":0}]```

#### Get list of dishes in restaurant by date

```curl -s 'http://localhost:8080/voting_system/restaurants/100002/dishes/by?date=2020-08-10' --user admin@gmail.com:admin```

Produces
```[{"id":100015,"name":"New York Cheesecake","price":350,"localDate":"2020-08-10"},{"id":100013,"name":"Caprese Burger","price":500,"localDate":"2020-08-10"},{"id":100014,"name":"Borsch","price":300,"localDate":"2020-08-10"}]```

#### Get certain dish

```curl -s 'http://localhost:8080/voting_system/restaurants/100002/dishes/100015' --user admin@gmail.com:admin```

Produces
```{"id":100015,"name":"New York Cheesecake","price":350,"localDate":"2020-08-10"}```

#### Vote for restaurant

```curl -s -X POST http://localhost:8080/voting_system/votes?restaurantId=100002 --user user@yandex.ru:password```

Produces
```{"id":100043,"date":"2020-10-26"}```

### Admin
#### Get all users

```curl -s  'http://localhost:8080/voting_system/admin/users' --user admin@gmail.com:admin```

Produces
```[{"id":100001,"name":"Admin","email":"admin@gmail.com","enabled":true,"registered":"2020-10-26T17:00:30.303+00:00","roles":["ADMIN"],"votes":null},{"id":100000,"name":"User","email":"user@yandex.ru","enabled":true,"registered":"2020-10-26T17:00:30.303+00:00","roles":["USER"],"votes":null}]```

#### Get one user

```curl -s  'http://localhost:8080/voting_system/admin/users/100000' --user admin@gmail.com:admin```

Produces
```{"id":100000,"name":"User","email":"user@yandex.ru","enabled":true,"registered":"2020-10-26T17:00:30.303+00:00","roles":["USER"],"votes":null}```

#### Get user by e-mail

```curl -s  'http://localhost:8080/voting_system/admin/users/by?email=user@yandex.ru' --user admin@gmail.com:admin```

Produces
```{"id":100000,"name":"User","email":"user@yandex.ru","enabled":true,"registered":"2020-10-26T17:00:30.303+00:00","roles":["USER"],"votes":null}```

#### Get user with votes

```curl -s  'http://localhost:8080/voting_system/admin/users/100000/with-votes' --user admin@gmail.com:admin```

Produces
```{"id":100000,"name":"User","email":"user@yandex.ru","enabled":true,"registered":"2020-10-26T17:19:10.174+00:00","roles":["USER"],"votes":[{"id":100008,"date":"2020-08-12"},{"id":100007,"date":"2020-08-11"},{"id":100006,"date":"2020-08-10"}]}```

#### Get user votes

```curl -s  'http://localhost:8080/voting_system/admin/users/100000/votes/' --user admin@gmail.com:admin```

Produces
```[{"id":100008,"localDate":"2020-08-12"},{"id":100007,"localDate":"2020-08-11"},{"id":100006,"localDate":"2020-08-10"}]```

#### Get user's vote today

```curl -s  'http://localhost:8080/voting_system/admin/users/100001/votes/today' --user admin@gmail.com:admin```

Produces
```{"id":100012,"localDate":"2020-11-16"}```

#### Get vote by date

```curl -s  'http://localhost:8080/voting_system/admin/users/100001/votes/by?date=2020-08-11' --user admin@gmail.com:admin```

Produces
```{"id":100010,"localDate":"2020-08-11"}```

#### Enable/disable user

```curl -s -X PATCH 'http://localhost:8080/voting_system/admin/users/100000?enabled=false' --user admin@gmail.com:admin```

#### Create new user

```curl -s -X POST -d '{"name":"newName","email":"newemail@ya.ru","password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting_system/admin/users --user admin@gmail.com:admin```

Produces
```{"id":100044,"name":"newName","email":"newemail@ya.ru","enabled":true,"registered":"2020-10-26T17:14:38.220+00:00"}```

#### Update user

```curl -s -X PUT -d '{"name":"newName","email":"newemail@ya.ru","password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting_system/admin/users/100000 --user admin@gmail.com:admin```

#### Delete user

```curl -s -X DELETE  'http://localhost:8080/voting_system/admin/users/100000' --user admin@gmail.com:admin```

#### Get restaurant 

```curl -s 'http://localhost:8080/voting_system/admin/restaurants/100002' --user admin@gmail.com:admin```

Produces
```{"id":100002,"name":"Corner Grill","enabled":true,"registered":"2020-10-26T17:19:10.176+00:00","dishes":null,"votes":null}```

#### Create new restaurant

```curl -s -X POST -d '{"name":"New","enabled":true,"registered":"2020-10-26T17:28:39.214+00:00"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting_system/admin/restaurants/ --user admin@gmail.com:admin```

Produces
```{"id":100043,"name":"New","enabled":true,"registered":"2020-10-26T17:28:39.214+00:00"}```

#### Update restaurant

```curl -s -X PUT -d '{"id":100002,"name":"UpdatedName","enabled":true,"registered":"2020-10-26T17:30:01.415+00:00"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting_system/admin/restaurants/100002 --user admin@gmail.com:admin```

#### Enable/disable restaurant

```curl -s -X PATCH 'http://localhost:8080/voting_system/admin/restaurants/100002?enabled=false' --user admin@gmail.com:admin```

#### Delete restaurant

```curl -s -X DELETE 'http://localhost:8080/voting_system/admin/restaurants/100002' --user admin@gmail.com:admin```

#### Get all dishes for restaurant

```curl -s 'http://localhost:8080/voting_system/admin/restaurants/100002/dishes/' --user admin@gmail.com:admin```

Produces
```[{"id":100022,"name":"Combo box","price":999,"localDate":"2020-11-16"},{"id":100021,"name":"Belgian Waffles","price":375,"localDate":"2020-08-12"},{"id":100019,"name":"Corsican Burger","price":500,"localDate":"2020-08-12"},{"id":100020,"name":"Tom Yum","price":400,"localDate":"2020-08-12"},{"id":100016,"name":"Bolognese Pasta","price":250,"localDate":"2020-08-11"},{"id":100017,"name":"Chicken Noodles","price":150,"localDate":"2020-08-11"},{"id":100018,"name":"Chiefs special","price":500,"localDate":"2020-08-11"},{"id":100014,"name":"Borsch","price":300,"localDate":"2020-08-10"},{"id":100013,"name":"Caprese Burger","price":500,"localDate":"2020-08-10"},{"id":100015,"name":"New York Cheesecake","price":350,"localDate":"2020-08-10"}]```


#### Get restaurant's dish

```curl -s 'http://localhost:8080/voting_system/admin/restaurants/100002/dishes/100013' --user admin@gmail.com:admin```

Produces
```{"id":100013,"name":"Caprese Burger","price":500,"date":"2020-08-10"}```

#### Create new dish

```curl -s -X POST -d '{"name":"New","price":1000,"date":"2020-10-26"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting_system/admin/restaurants/100002/dishes --user admin@gmail.com:admin```

Produces
```{"id":100044,"name":"New","price":1000,"date":"2020-10-26"}```

#### Update dish

```curl -s -X PUT -d '{"id":100013,"name":"UpdatedName","price":500,"date":"2020-08-10"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting_system/admin/restaurants/100002/dishes/100013 --user admin@gmail.com:admin```

#### Delete dish

```curl -s -X DELETE 'http://localhost:8080/voting_system/admin/restaurants/100002/dishes/100013' --user admin@gmail.com:admin```