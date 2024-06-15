import  {useEffect, useState} from 'react';
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const Dashboard = () => {
    const [temperatureData, setTemperatureData] = useState([]);
    // const [temp,setTemp] = useState({
    //   device_id: 9999,
    //   temperature: 24.99,
    //   //java LoocalDateTime format
    //   timestamp: "2024-04-13 22:27:45"
    // });


    useEffect(() => {
        const token = sessionStorage.getItem('token');
        console.log("Token:", token);

        const socket = new SockJS(`http://localhost:8080/temperature`);
        const stompClient = Stomp.over(socket);

        stompClient.connect({ Authorization: `Bearer ${token}` }, () => {
            console.log("Connected to WebSocket");

            stompClient.subscribe("/topic/temperature", (message) => {
                const tempData = JSON.parse(message.body);
                setTemperatureData((prevData) => [...prevData, tempData]);
            });
        }, (error) => {
            console.error("Connection error", error);
        });

        return () => {
            if (stompClient) {
                stompClient.disconnect(() => {
                    console.log("Disconnected from WebSocket");
                });
            }
        };
    }, []);

    return (
        <div>
            <>
                <div>
                    <h1>Temperature Monitor</h1>
                    <ul>
                        {temperatureData.map((data, index) => (
                            <li key={index}>
                                Device ID: {data.device_id} | Temperature: {data.temperature}°C |
                                Timestamp: {data.timestamp}
                            </li>
                        ))}
                    </ul>
                </div>
            </>
        </div>
    );
};

export default Dashboard;