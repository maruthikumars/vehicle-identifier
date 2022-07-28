--CREATE TABLE vehicle(
--   id INT NOT NULL,
--   vehicle_type VARCHAR (50) NOT NULL,
--   powertrain  VARCHAR (50) ,
--   PRIMARY KEY (id)
--);

--oneToOne
--CREATE TABLE frame (
--   id INT NOT NULL,
--   material VARCHAR (50) NOT NULL,
--   vehicle_id INT references vehicle(id),
--   PRIMARY KEY (id)
--);
--
----oneToMany
--CREATE TABLE wheel (
--   id INT NOT NULL,
--   position VARCHAR (50) NOT NULL,
--   material  VARCHAR (50) NOT NULL,
--   vehicle_id INT references vehicle(id),
--   PRIMARY KEY (id)
--);
--


CREATE TABLE vehicle (
   vehicle_type VARCHAR (50) NOT NULL,
   material  VARCHAR (50) NOT NULL,
   position VARCHAR (50) NOT NULL,
   powertrain VARCHAR (50) NOT NULL,
   PRIMARY KEY (vehicle_type, position)
);
insert into vehicle(vehicle_type, material,position, powertrain) values('Big Wheel', 'plastic', 'left rear', 'human');
insert into vehicle(vehicle_type, material,position, powertrain) values('Big Wheel', 'plastic', 'right rear', 'human');
insert into vehicle(vehicle_type, material,position, powertrain) values('Big Wheel', 'plastic', 'front', 'human');

--select distinct(vehicle_type) from VEHICLE  where
--material='plastic'
--and position in ('left rear', 'right rear', 'front')
--and powertrain='human';