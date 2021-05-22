# Producer service


### Running the example

To run the example, execute ``./start.sh``

### Endpoints:

**Book**:
  * http://localhost:9090/api/book
        
Request:  

```json
    [
        {
            "name": "Lisa Morgan",
            "gender": "F"
        },
        {
            "name": "Chris Havens",
            "gender": "M"
        }
    ]
```        
    
Response:  
    
```json
    {
        "message": "successfully booked the reservation",
        "code": 201,
        "body": [
            {
                "name": "Lisa Morgan",
                "gender": "F",
                "referenceId": "a359ca7b-9ba2-40c7-b963-0f018066ff5622052021"
            },
            {
                "name": "Chris Havens",
                "gender": "M",
                "referenceId": "43ac0c39-8bde-4446-b71a-becdebdb3b1622052021"
            }
        ]
    }
```    
    

**Cancelling a booking**  
  * http://localhost:9090/api/cancelBooking/{referenceId}.  
    Ex: http://localhost:9090/api/cancelBooking/43ac0c39-8bde-4446-b71a-becdebdb3b1622052
    

Response:   

```json
    {
        "message": "Cancelled the booking successfully for the ID - " 43ac0c39-8bde-4446-b71a-becdebdb3b1622052.",
        "code": 400,
        "body": []
    }
```    
  
      