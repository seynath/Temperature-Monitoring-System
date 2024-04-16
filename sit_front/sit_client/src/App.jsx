import { useState, useEffect } from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
// import axios from "axios";


import "./App.css";

function App() {
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

    stompClient.connect({}, () => {
      stompClient.subscribe("/topic/temperature",
       (message) => {
        const tempData = JSON.parse(message.body);
        setTemperatureData((prevData) => [...prevData, tempData]);
      }
    );
    });
  }, []);

  // useEffect(() => {
  //   axios.post("http://localhost:8080/api/v/temperature/save-temp", temp)
  //     .then((response) => {
  //       setTemperatureData(response.data);
  //     });
  // }
  // , []);

  

  return (
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
  );
}

export default App;
