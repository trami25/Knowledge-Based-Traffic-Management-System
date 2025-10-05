# Knowledge-Based-Traffic-Management-System

## Test scenarios


POST http://localhost:8080/api/rules/execute
Content-Type: application/json

{
  "facts": [
    {
      "type": "Crossroad",
      "payload": {
        "id": "Stadium_Area",
        "connectedRoads": ["Stadium_Rd", "Fan_Zone", "Parking_Complex"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "Metro_Station",
        "connectedRoads": ["Metro_Exit", "Bus_Terminal", "Pedestrian_Plaza"]
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Stadium_Area",
        "value": 85,
        "timestamp": 1696518000000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Metro_Station",
        "value": 82,
        "timestamp": 1696518300000
      }
    },
    {
      "type": "Accident",
      "payload": {
        "crossroad": "Stadium_Area",
        "severity": "high",
        "timestamp": 1696518600000
      }
    },
    {
      "type": "Accident",
      "payload": {
        "crossroad": "Metro_Station",
        "severity": "medium",
        "timestamp": 1696518900000
      }
    },
    {
      "type": "PedestrianDetected",
      "payload": {
        "crossroad": "Stadium_Area",
        "count": 45,
        "timestamp": 1696519200000
      }
    },
    {
      "type": "PublicTransportDelay",
      "payload": {
        "line": "Metro_Line_Blue",
        "delayMinutes": 18,
        "timestamp": 1696519500000
      }
    }
  ],
  "queries": []
}



POST http://localhost:8080/api/rules/execute
Content-Type: application/json

{
  "facts": [
    {
      "type": "Crossroad",
      "payload": {
        "id": "Highway_Junction",
        "connectedRoads": ["Highway_A1", "Highway_B2", "City_Access", "Emergency_Route"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "Hospital_District",
        "connectedRoads": ["Hospital_Main", "Emergency_Access", "Medical_Center"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "School_Zone",
        "connectedRoads": ["School_St", "Residential_Ave", "Safety_Crossing"]
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Highway_Junction",
        "value": 92,
        "timestamp": 1696518000000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Hospital_District",
        "value": 88,
        "timestamp": 1696518300000
      }
    },
    {
      "type": "Accident",
      "payload": {
        "crossroad": "Highway_Junction",
        "severity": "high",
        "timestamp": 1696518600000
      }
    },
    {
      "type": "Accident",
      "payload": {
        "crossroad": "Hospital_District",
        "severity": "high",
        "timestamp": 1696518900000
      }
    },
    {
      "type": "Accident",
      "payload": {
        "crossroad": "School_Zone",
        "severity": "medium",
        "timestamp": 1696519200000
      }
    },
    {
      "type": "PedestrianDetected",
      "payload": {
        "crossroad": "School_Zone",
        "count": 60,
        "timestamp": 1696519500000
      }
    },
    {
      "type": "PedestrianDetected",
      "payload": {
        "crossroad": "Hospital_District",
        "count": 35,
        "timestamp": 1696519800000
      }
    },
    {
      "type": "PublicTransportDelay",
      "payload": {
        "line": "Emergency_Bus_Route",
        "delayMinutes": 25,
        "timestamp": 1696520100000
      }
    },
    {
      "type": "PublicTransportDelay",
      "payload": {
        "line": "Hospital_Shuttle",
        "delayMinutes": 15,
        "timestamp": 1696520400000
      }
    },
    {
      "type": "Weather",
      "payload": {
        "type": "snow",
        "intensity": "heavy",
        "timestamp": 1696520700000
      }
    },
    {
      "type": "Weather",
      "payload": {
        "type": "ice",
        "intensity": "high",
        "timestamp": 1696521000000
      }
    }
  ],
  "queries": []
}

POST http://localhost:8080/api/rules/execute
Content-Type: application/json

{
  "facts": [
    {
      "type": "Crossroad",
      "payload": {
        "id": "Airport_Terminal",
        "connectedRoads": ["Airport_Access", "Terminal_Loop", "Departure_Gate"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "Hotel_District",
        "connectedRoads": ["Hotel_Avenue", "Conference_Center", "Business_Park"]
      }
    },
    {
      "type": "EventDay",
      "payload": {
        "name": "International_Business_Summit",
        "expectedTraffic": 1200
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Airport_Terminal",
        "value": 40,
        "timestamp": 1696517100000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Hotel_District",
        "value": 45,
        "timestamp": 1696517400000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Airport_Terminal",
        "value": 78,
        "timestamp": 1696518000000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Hotel_District",
        "value": 82,
        "timestamp": 1696518300000
      }
    }
  ],
  "queries": []
}


POST http://localhost:8080/api/rules/execute
Content-Type: application/json

{
  "facts": [
    {
      "type": "Crossroad",
      "payload": {
        "id": "Shopping_Mall",
        "connectedRoads": ["Mall_Entrance", "Food_Court", "Cinema_Complex"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "Business_Center",
        "connectedRoads": ["Corporate_Plaza", "Financial_District", "Tech_Hub"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "Transport_Hub",
        "connectedRoads": ["Train_Station", "Bus_Terminal", "Metro_Connection"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "University_Campus",
        "connectedRoads": ["Campus_Main", "Student_Housing", "Research_Center"]
      }
    },
    {
      "type": "EventDay",
      "payload": {
        "name": "City_Wide_Festival",
        "expectedTraffic": 2800
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Shopping_Mall",
        "value": 78,
        "timestamp": 1696518000000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Business_Center",
        "value": 82,
        "timestamp": 1696518300000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Transport_Hub",
        "value": 85,
        "timestamp": 1696518600000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "University_Campus",
        "value": 79,
        "timestamp": 1696518900000
      }
    }
  ],
  "queries": []
}



POST http://localhost:8080/api/rules/execute
Content-Type: application/json

{
  "facts": [
    {
      "type": "Crossroad",
      "payload": {
        "id": "Central_Plaza",
        "connectedRoads": ["North_Avenue", "South_Street", "East_Boulevard"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "West_Terminal",
        "connectedRoads": ["West_Highway", "Industrial_Zone", "Port_Access"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "North_Gateway",
        "connectedRoads": ["Highway_North", "Residential_District", "Suburban_Link"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "South_Junction",
        "connectedRoads": ["Highway_South", "Airport_Link", "Commercial_Zone"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "East_Connector",
        "connectedRoads": ["East_Highway", "Beach_Access", "Tourist_District"]
      }
    },
    {
      "type": "EventDay",
      "payload": {
        "name": "National_Holiday_Celebration",
        "expectedTraffic": 18000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Central_Plaza",
        "value": 50,
        "timestamp": 1696517100000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "West_Terminal",
        "value": 48,
        "timestamp": 1696517400000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Central_Plaza",
        "value": 88,
        "timestamp": 1696518000000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "West_Terminal",
        "value": 85,
        "timestamp": 1696518300000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "North_Gateway",
        "value": 82,
        "timestamp": 1696518600000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "South_Junction",
        "value": 79,
        "timestamp": 1696518900000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "East_Connector",
        "value": 86,
        "timestamp": 1696519200000
      }
    }
  ],
  "queries": []
}

POST http://localhost:8080/api/rules/execute
Content-Type: application/json

{
  "facts": [
    {
      "type": "Crossroad",
      "payload": {
        "id": "Olympic_Stadium",
        "connectedRoads": ["Olympic_Ave", "Champion_Blvd", "Victory_Lane"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "North_Parking",
        "connectedRoads": ["North_Gate", "Shuttle_Loop", "Metro_Link"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "South_Parking",
        "connectedRoads": ["South_Gate", "Bus_Terminal", "Fan_Zone"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "East_Entrance",
        "connectedRoads": ["VIP_Access", "Media_Center", "Press_Zone"]
      }
    },
    {
      "type": "Crossroad",
      "payload": {
        "id": "West_Complex",
        "connectedRoads": ["Vendor_Area", "Food_Court", "Merchandise"]
      }
    },
    {
      "type": "EventDay",
      "payload": {
        "name": "Olympic_Games_Final",
        "expectedTraffic": 25000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Olympic_Stadium",
        "value": 35,
        "timestamp": 1696516800000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "Olympic_Stadium",
        "value": 92,
        "timestamp": 1696518000000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "North_Parking",
        "value": 88,
        "timestamp": 1696518300000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "South_Parking",
        "value": 85,
        "timestamp": 1696518600000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "East_Entrance",
        "value": 90,
        "timestamp": 1696518900000
      }
    },
    {
      "type": "TrafficDensity",
      "payload": {
        "crossroad": "West_Complex",
        "value": 83,
        "timestamp": 1696519200000
      }
    },
    {
      "type": "Accident",
      "payload": {
        "crossroad": "Olympic_Stadium",
        "severity": "high",
        "timestamp": 1696519500000
      }
    },
    {
      "type": "Accident",
      "payload": {
        "crossroad": "North_Parking",
        "severity": "medium",
        "timestamp": 1696519800000
      }
    },
    {
      "type": "PedestrianDetected",
      "payload": {
        "crossroad": "South_Parking",
        "count": 80,
        "timestamp": 1696520100000
      }
    },
    {
      "type": "PedestrianDetected",
      "payload": {
        "crossroad": "East_Entrance",
        "count": 95,
        "timestamp": 1696520400000
      }
    },
    {
      "type": "PublicTransportDelay",
      "payload": {
        "line": "Olympic_Express",
        "delayMinutes": 30,
        "timestamp": 1696520700000
      }
    },
    {
      "type": "PublicTransportDelay",
      "payload": {
        "crossroad": "Metro_Olympic_Line",
        "delayMinutes": 22,
        "timestamp": 1696521000000
      }
    },
    {
      "type": "Weather",
      "payload": {
        "type": "thunderstorm",
        "intensity": "heavy",
        "timestamp": 1696521300000
      }
    }
  ],
  "queries": []
}