
-- Table to store user information
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    userName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Password varchar(50),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table to store information about different internet services
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

-- Table to store information about different TV services
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

-- Table to link users with the TV services they have availed
CREATE TABLE TvServicesAvailed (
    UserID INT,
    ServiceID INT,
    StartDate DATE,
    EndDate DATE,
    PRIMARY KEY (UserID, ServiceID, StartDate),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ServiceID) REFERENCES TvServices(ServiceID)
);

-- Table to link users with the internet services they have availed
CREATE TABLE InternetServicesAvailed (
    UserID INT,
    ServiceID INT,
    StartDate DATE,
    EndDate DATE,
    PRIMARY KEY (UserID, ServiceID, StartDate),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
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

