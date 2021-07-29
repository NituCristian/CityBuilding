INSERT INTO City VALUES (1, 'Deva', 34.0, 70400, 187, 1269,  45.8833, 22.9, 1, 1, 256, 256);

INSERT INTO CityLayers VALUES (1, 'Amenities', './/NoNeighourhoods.jpg', 1);
INSERT INTO CityLayers VALUES (2, 'Neighbourhoods and amenities', './/Neighourhoods.jpg', 1);

INSERT INTO neighbourhood VALUES (1, 'Centrul Vechi', 0, 1);
INSERT INTO neighbourhood VALUES (2, 'Dacia', 0, 1);
INSERT INTO neighbourhood VALUES (3, 'Progresul', 0, 1);
INSERT INTO neighbourhood VALUES (4, 'Emanoil Gojdu', 0, 1);

update neighbourhood set Rating = 0 where id = 3;

INSERT INTO `Type` VALUES (1, 'museum', 'A museum: an institution with exhibitions on scientific, historical, cultural topics', './/icons//museum.jpg');
INSERT INTO `Type` VALUES (2, 'memorial', 'Small memorials, usually remembering special persons, people who lost their lives in the wars, past events or missing places.', './/icons//house.png');
INSERT INTO `Type` VALUES (3, 'town_hall', 'Building where the administration of a village, town or city may be located, or just a community meeting place', './/icons//townhall.png');
INSERT INTO `Type` VALUES (4, 'pub', 'A place selling beer and other alcoholic drinks', './/icons//pub.png');
INSERT INTO `Type` VALUES (5, 'hotel', 'A building designed with separate rooms available for overnight accommodation', './/icons//hotel.png');
INSERT INTO `Type` VALUES (6, 'hospital', 'A building which forms part of a hospital.', './/icons//hospital.png');
INSERT INTO `Type` VALUES (7, 'park', 'A park, usually urban (municipal).', './/icons//park.jpg');
INSERT INTO `Type` VALUES (8, 'bank', 'Bank or credit union: a financial establishment where customers can deposit and withdraw money, take loans, make investments and transfer funds.', './/icons//bank.jpg');
INSERT INTO `Type` VALUES (9, 'water_park', 'An amusement park with features like water slides, recreational pools (e.g. wave pools) or lazy rivers.', './/icons//waterpark.jpg');
INSERT INTO `Type` VALUES (10, 'church', 'A building that was built as a church.', './/icons//church.png');
INSERT INTO `Type` VALUES (11, 'mall', 'A shopping mall – multiple stores under one roof (also known as a shopping centre', './/icons//mall.jpg');
INSERT INTO `Type` VALUES (12, 'post_office', 'Post office building with postal services', './/icons//postOffice.png');
INSERT INTO `Type` VALUES (13, 'police_station', 'Building that serves as a primary point of contact for the public, i.e. public-facing police facilities.', './/icons//policeStation.jpg');
INSERT INTO `Type` VALUES (14, 'water', 'Object that can be build on water', './/icons//water.png');
INSERT INTO `Type` VALUES (15, 'factory', 'Big pollution building', './/icons//factory.jpg');


INSERT INTO Amenity VALUES (1, 1, 1, 10, 460, 20, 480, 4, 'Strada 1 Decembrie 39', TRUE, FALSE, 'Muzeul Civilizatiei Dacice si Romane',  'Muzeu organizat în trei sectii: istorie, stiintele naturii si arta');
INSERT INTO Amenity VALUES (2, 1, 2, 50, 410, 65, 440, 3.6, 'Strada Avram Iancu H3', TRUE, FALSE, 'Casa Memoriala Dr Petru Groza',  'Casa unde a trait Petru Groza');
INSERT INTO Amenity VALUES (3, 1, 3, 140, 390, 160, 410, 3.2, 'Piata Unirii 4', TRUE, FALSE, 'Primaria Deva', 'Primaria Municipiului Deva');
INSERT INTO Amenity VALUES (4, 1, 4, 70, 350, 90, 360, 2.7, 'Piata Unirii 7', TRUE, FALSE, 'Fischer Pub', 'Fischer Pub este o alegere excelenta pentru petrecerea timpului liber');
INSERT INTO Amenity VALUES (5, 2, 5, 400, 410, 420, 430, 2.6, 'Strada Maresal Averescu', TRUE, FALSE, 'Hotel Sarmis', 'Aflat la 12 minute de mers pe jos de gara din Deva, acest hotel modest, situat intr-o zona comerciala cu magazine si restaurante, se afla la 2 km de Cetatea Deva');
INSERT INTO Amenity VALUES (6, 2, 6, 360, 380, 375, 400, 3.0, 'Bulevardul 22 Decembrie 58', TRUE, FALSE, 'Spitalul Judetean de Urgenta', 'Principalul spital al orasului');
INSERT INTO Amenity VALUES (7, 2, 7, 430, 370, 450, 385, 4.3, 'Bulebardul Decebal', TRUE, FALSE,  'Parcul Operei', 'Un loc destinat destinderii fiecaruia');
INSERT INTO Amenity VALUES (8, 2, 8, 480, 470, 490, 475, 3.1, 'Strada Carpati Nr.2,', TRUE, FALSE, 'Banca Comerciala Romana', 'Principala Banca a Romaniei');
INSERT INTO Amenity VALUES (9, 3, 9, 185, 200, 205, 220, 2.4, 'Strada Axente Sever 34', TRUE, FALSE, 'Aqualand Deva', 'Un complex full de distractie');
INSERT INTO Amenity VALUES (10, 3, 5, 100, 90, 120, 105, 2.8, 'Strada Horea 26', TRUE, FALSE, 'Casa Branga B&B', 'Casa Branga va ofera cazare de 3 stele dupa cele mai noi standarde');
INSERT INTO Amenity VALUES (11, 3, 10, 180, 70, 195, 85, 3.5, 'Strada Progresului 6', TRUE, FALSE, 'Manastirea Franciscana', 'Manastirea franciscana a fost ridicata intre anii 1669 si 1752, pe locul donat ordinului franciscan de catre judecatorul regal Lazar Istvan');
INSERT INTO Amenity VALUES (12, 3, 8, 30, 140, 50, 150, 3.1, 'Bloc R, Bulevardul Decebal', TRUE, FALSE, 'ING Groep', 'UNG Groep N.V. este un grup bancar din Olanda prezent la nivel mondial, cu peste 60 milioane clienti individuali, 115000 angajati si subsidiare in 50 tari');
INSERT INTO Amenity VALUES (13, 4, 11, 350, 100, 375, 125, 2.0, 'Bulevardul Iuliu Maniu 28', TRUE, FALSE, 'Deva Mall', 'Un loc versatil pentru a-ti petrece timpul');
INSERT INTO Amenity VALUES (14, 4, 12, 420, 140, 435, 155, 2.9, 'Aleea Pacii 4', TRUE, FALSE, 'Oficiul Postal Deva 3', 'Posta Romana');
INSERT INTO Amenity VALUES (15, 4, 9, 320, 190, 340, 200, 3.2, 'Calea Zarandului', TRUE, FALSE,  'Strandul Municipal nr2', 'Un loc minunat pentru o zi insorita');


INSERT INTO Photo VALUES (1, 1, 256, 512, '.\dacicMuseum.png');
INSERT INTO Photo VALUES (2, 2, 512, 512, '.\memorialHouse.jpg');
INSERT INTO Photo VALUES (3, 3, 512, 512, '.\primarie.jpg');
INSERT INTO Photo VALUES (4, 4, 512, 512, '.\fischer.jpg');
INSERT INTO Photo VALUES (5, 5, 512, 512, '.\sarmis1.jpg');
INSERT INTO Photo VALUES (6, 5, 512, 512, '.\sarmis2.jpg');
INSERT INTO Photo VALUES (7, 5, 512, 512, '.\sarmis3.jpg');
INSERT INTO Photo VALUES (8, 6, 512, 512, '.\spital.jpg');
INSERT INTO Photo VALUES (9, 7, 512, 512, '.\parcOpera.jpg');
INSERT INTO Photo VALUES (10, 8, 512, 512, '.\BCRDeva.jpg');
INSERT INTO Photo VALUES (11, 9, 512, 512, '.\aqualand1.jpg');
INSERT INTO Photo VALUES (12, 9, 512, 512, '.\aqualand2.jpg');
INSERT INTO Photo VALUES (13, 10, 512, 512, '.\branga1.jpg');
INSERT INTO Photo VALUES (14, 10, 512, 512, '.\branga2.jpg');
INSERT INTO Photo VALUES (15, 11, 512, 512, '.\franciscan.jpg');
INSERT INTO Photo VALUES (16, 12, 512, 512, '.\ING.jpg');
INSERT INTO Photo VALUES (17, 13, 512, 512, '.\DevaMall.jpg');
INSERT INTO Photo VALUES (18, 14, 512, 512, '.\PostaDeva.jpg');
INSERT INTO Photo VALUES (19, 15, 512, 512, '.\Strand.jpg');

INSERT INTO Contact VALUES (1, 1, '0743785398', 'dacicMuseum@gmail.com');
INSERT INTO Contact VALUES (2, 2, '0744485398', 'casaPetruGroza@gmail.com');
INSERT INTO Contact VALUES (3, 3, '0755485398', 'primarieDeva@gmail.com');
INSERT INTO Contact VALUES (4, 4, '0722485398', 'fischerPub@gmail.com');
INSERT INTO Contact VALUES (5, 5, '0722185398', 'sarmisHotel@gmail.com');
INSERT INTO Contact VALUES (6, 6, '0712185398', 'spitalJudetean@gmail.com');
INSERT INTO Contact VALUES (7, 8, '0776185498', 'BCRDeva@gmail.com');
INSERT INTO Contact VALUES (8, 9, '0736927972', 'aqualand@gmail.com');
INSERT INTO Contact VALUES (9, 10, '0736924472', 'branga@gmail.com');
INSERT INTO Contact VALUES (10, 11, '0736924455', 'franciscana@gmail.com');
INSERT INTO Contact VALUES (11, 12, '0736994455', 'INGDeva@gmail.com');
INSERT INTO Contact VALUES (12, 13, '0743854353', 'devaMall@.gmail.com');
INSERT INTO Contact VALUES (13, 14, '0741114353', 'postaDeva@.gmail.com');
INSERT INTO Contact VALUES (14, 15, '0741114993', 'strandDeva@.gmail.com');
