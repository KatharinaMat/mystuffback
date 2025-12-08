-- Created by Redgate Data Modeler (https://datamodeler.redgate-platform.com)
-- Last modification date: 2025-11-28 09:16:31.572

-- tables
-- Table: itemImage
CREATE TABLE itemImage (
                       id serial  NOT NULL,
                       item_id int  NOT NULL,
                       image_data bytea  NOT NULL,
                       CONSTRAINT image_pk PRIMARY KEY (id)
);

-- Table: item
CREATE TABLE item (
                      id serial  NOT NULL,
                      user_id int  NOT NULL,
                      name varchar(50)  NOT NULL,
                      date date  NOT NULL,
                      model varchar(250)  NULL,
                      comment varchar(500)  NULL,
                      status varchar(1)  NOT NULL,
                      CONSTRAINT item_pk PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE role (
                      id serial  NOT NULL,
                      name varchar(20)  NOT NULL,
                      CONSTRAINT role_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE "user" (
                        id serial  NOT NULL,
                        role_id int  NOT NULL,
                        username varchar(50)  NOT NULL,
                        password varchar(50)  NOT NULL,
                        email varchar(50)  NOT NULL,
                        status varchar(1)  NOT NULL,
                        CONSTRAINT id PRIMARY KEY (id)
);

-- foreign keys
-- Reference: item_user (table: item)
ALTER TABLE item ADD CONSTRAINT item_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: receipt_image_item (table: itemImage)
ALTER TABLE itemImage ADD CONSTRAINT receipt_image_item
    FOREIGN KEY (item_id)
        REFERENCES item (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_role (table: user)
ALTER TABLE "user" ADD CONSTRAINT user_role
    FOREIGN KEY (role_id)
        REFERENCES role (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

