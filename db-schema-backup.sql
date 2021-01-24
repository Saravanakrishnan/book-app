DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`
(
    `book_id`        bigint       NOT NULL AUTO_INCREMENT,
    `title`          varchar(75)  NOT NULL,
    `authors`        varchar(100) NOT NULL,
    `average_rating` tinytext     NOT NULL,
    `language_code`  varchar(10)  NOT NULL,
    `rating_count`   varchar(100) NOT NULL,
    `price`          float        NOT NULL DEFAULT '0',
    PRIMARY KEY (`book_id`),
    KEY `idx_title` (`title`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 40146
  DEFAULT CHARSET = utf8mb4;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`
(
    `id`        bigint       NOT NULL AUTO_INCREMENT,
    `userId`    bigint                DEFAULT NULL,
    `sessionId` varchar(100) NOT NULL,
    `token`     varchar(100) NOT NULL,
    `status`    smallint     NOT NULL DEFAULT '0',
    `mobile`    varchar(15)           DEFAULT NULL,
    `email`     varchar(50)           DEFAULT NULL,
    `createdAt` datetime     NOT NULL,
    `updatedAt` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_cart_user` (`userId`),
    CONSTRAINT `fk_cart_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item`
(
    `id`        bigint     NOT NULL AUTO_INCREMENT,
    `bookId`    bigint     NOT NULL,
    `cartId`    bigint     NOT NULL,
    `price`     float      NOT NULL DEFAULT '0',
    `quantity`  smallint   NOT NULL DEFAULT '0',
    `active`    tinyint(1) NOT NULL DEFAULT '0',
    `createdAt` datetime   NOT NULL,
    `updatedAt` datetime            DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_cart_item_cart` (`cartId`),
    KEY `idx_cart_item_product` (`bookId`),
    CONSTRAINT `fk_cart_item_book` FOREIGN KEY (`bookId`) REFERENCES `book` (`book_id`),
    CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cartId`) REFERENCES `cart` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 44
  DEFAULT CHARSET = utf8mb4;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`
(
    `image_id`   bigint       NOT NULL AUTO_INCREMENT,
    `image_url`  varchar(500) NOT NULL,
    `is_deleted` tinyint      NOT NULL DEFAULT '0',
    PRIMARY KEY (`image_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4;

--
-- Dumping data for table `image`
--

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`        bigint       NOT NULL AUTO_INCREMENT,
    `userId`    bigint                DEFAULT NULL,
    `sessionId` varchar(100) NOT NULL,
    `token`     varchar(100) NOT NULL,
    `status`    smallint     NOT NULL DEFAULT '0',
    `total`     float        NOT NULL DEFAULT '0',
    `createdAt` datetime     NOT NULL,
    `updatedAt` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_order_user` (`userId`),
    CONSTRAINT `fk_order_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`
(
    `id`        bigint   NOT NULL AUTO_INCREMENT,
    `bookId`    bigint   NOT NULL,
    `orderId`   bigint   NOT NULL,
    `price`     float    NOT NULL DEFAULT '0',
    `quantity`  smallint NOT NULL DEFAULT '0',
    `createdAt` datetime NOT NULL,
    `updatedAt` datetime          DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_order_item_order` (`orderId`),
    KEY `idx_order_item_product` (`bookId`),
    CONSTRAINT `fk_order_item_book` FOREIGN KEY (`bookId`) REFERENCES `book` (`book_id`),
    CONSTRAINT `fk_order_item_order` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction`
(
    `id`        bigint       NOT NULL AUTO_INCREMENT,
    `userId`    bigint       NOT NULL,
    `orderId`   bigint       NOT NULL,
    `code`      varchar(100) NOT NULL,
    `type`      smallint     NOT NULL DEFAULT '0',
    `mode`      smallint     NOT NULL DEFAULT '0',
    `status`    smallint     NOT NULL DEFAULT '0',
    `createdAt` datetime     NOT NULL,
    `updatedAt` datetime              DEFAULT NULL,
    `content`   text,
    PRIMARY KEY (`id`),
    KEY `idx_transaction_order` (`orderId`),
    KEY `idx_transaction_user` (`userId`),
    CONSTRAINT `fk_transaction_order` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`),
    CONSTRAINT `fk_transaction_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`           bigint     NOT NULL AUTO_INCREMENT,
    `email`        varchar(50)         DEFAULT NULL,
    `firstName`    varchar(50)         DEFAULT NULL,
    `mobile`       varchar(15)         DEFAULT NULL,
    `registeredAt` datetime   NOT NULL,
    `admin`        tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_email` (`email`),
    UNIQUE KEY `uq_mobile` (`mobile`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Dump completed on 2021-01-24 16:25:49
