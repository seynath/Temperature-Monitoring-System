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
        const socket = new SockJS("http://localhost:8080/temperature");
        const stompClient = Stomp.over(socket);
        //tried on this way but not working
        // const token = sessionStorage.getItem("token");
        // stompClient.connect({Authorization: `Bearer ${token}`}, () => {
        //     stompClient.subscribe("/topic/temperature",
        //         (message) => {
        //             const tempData = JSON.parse(message.body);
        //             setTemperatureData((prevData) => [...prevData, tempData]);
        //         }
        //     );
        // });
        stompClient.connect({}, () => {
            stompClient.subscribe("/topic/temperature",
                (message) => {
                    const tempData = JSON.parse(message.body);
                    setTemperatureData((prevData) => [...prevData, tempData]);
                }
            );
        });
    }, []);
    return (
        <div>
            <>
                <div>
                    <iframe
                        width="full"
                        height="800"
                        seamless
                        src="http://172.16.16.103:8088/superset/dashboard/p/evkPGvOnD5w/"
                    >
                    </iframe>
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