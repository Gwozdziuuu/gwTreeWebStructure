CREATE TABLE `tree` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nodeData` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `nodeValue` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `test`.`tree`
(`id`,
`nodeData`,
`parentId`,
`nodeValue`)
VALUES
(1,
'root',
0,
0);
