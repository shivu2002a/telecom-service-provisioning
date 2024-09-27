
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    userName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Role varchar(20) Not NULL,
    Password varchar(50),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE InternetServices (
    ServiceID INT PRIMARY KEY AUTO_INCREMENT,
    ServiceName VARCHAR(100) NOT NULL,
    Description TEXT,
    serviceType varchar(50),
    serviceDownloadSpeed INT,
    serviceUploadSpeed INT,
    benefits TEXT,
    criteria text,
    active boolean,
    MonthlyCost DECIMAL(10, 2)
);

CREATE TABLE TvServices (
    ServiceID INT PRIMARY KEY AUTO_INCREMENT,
    ServiceName VARCHAR(100) NOT NULL,
    Description TEXT,
    benefits text,
    serviceType text,
    criteria text,
    active boolean,
    MonthlyCost DECIMAL(10, 2)
);

CREATE TABLE TvServicesAvailed (
    UserID INT,
    ServiceID INT,
    StartDate DATE,
    EndDate DATE,
    PRIMARY KEY (UserID, ServiceID, StartDate),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ServiceID) REFERENCES TvServices(ServiceID)
);

CREATE TABLE InternetServicesAvailed (
    UserID INT,
    ServiceID INT,
    StartDate DATE,
    EndDate DATE,
    PRIMARY KEY (UserID, ServiceID, StartDate),
    FOREIGN KEY (UserID() REFERENCES Users(UserID),
    FOREIGN KEY (ServiceID) REFERENCES InternetServices(ServiceID)
);

CREATE TABLE PendingRequests (
    requestID INT,
    ServiceID INT,
    userID INT,
    serviceType VARCHAR(20),
    reuestStatus VARCHAR(15),
    remarks VARCHAR(100),
    active boolean
)

CREATE TABLE InternetServiceFeedbacks (
    id INT AUTO_INCREMENT,
    feedback varchar(200),
    username varchar(50),
    ServiceID INT, 
    FOREIGN KEY (ServiceID) REFERENCES InternetServices(ServiceID)
)

CREATE TABLE TvServiceFeedbacks (
    id INT AUTO_INCREMENT,
    feedback varchar(200),
    username varchar(50),
    ServiceID INT, 
    FOREIGN KEY (ServiceID) REFERENCES TvServices(ServiceID)
)

