import { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import LinearProgress from '@mui/material/LinearProgress';
import { styled } from '@mui/material/styles';
import { Accordion, AccordionDetails, AccordionSummary, Box, Button, Card, CardHeader, Typography } from '@mui/material';
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const ColorfulBar = styled(LinearProgress)(({ theme }) => ({
    height: 10,
    borderRadius: 5,
    backgroundColor: theme.palette.grey[300],
    '& .MuiLinearProgress-bar': {
      background: 'linear-gradient(90deg, red, orange, yellow, green, blue, indigo, violet)',
    },
  }));

  const Dashboard = () => {
    const [machineData, setMachineData] = useState({
        1: { device_id: '', temperature: '', timestamp: '' },
        2: { device_id: '', temperature: '', timestamp: '' },
        3: { device_id: '', temperature: '', timestamp: '' },
        4: { device_id: '', temperature: '', timestamp: '' },
    });

    const [temperatureData, setTemperatureData] = useState([]);

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

    const downloadCSV = () => {
        const csvData = [
          ["Device ID", "Temperature", "Timestamp"],
          ...temperatureData.map((data) => [
            data.device_id,
            `${data.temperature}°C`,
            data.timestamp,
          ]),
        ].map((row) => row.join(","));
    
        const blob = new Blob([csvData.join("\n")], { type: "text/csv" });
        const a = document.createElement("a");
        const url = window.URL.createObjectURL(blob);
        a.href = url;
        a.download = "data.csv";
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
      };

    return (
        <div>

                <div>
                    <iframe
                        width="100%"
                        height="800"
                        seamless
                        src="http://172.16.16.103:8088/superset/dashboard/p/0d8QKLbzbZL/"
                    >
                    </iframe>

                </div>
        <h1 className='text-2xl lg:text-7xl text-center font-bold pb-10 pt-10'>Temperature Monitor</h1>
        <Box>
        <Card className="mt-1">
          <CardHeader
            title={"All Devices Temperature Details"}
            sx={{ pt: 2, alignItems: "center" }}
            action={
              <Button variant="contained" color="primary" onClick={downloadCSV}>
                Download Report
              </Button>
            }
          />
            <Box sx={{ width: '100%', mt: 1 }}>
                <ColorfulBar variant="determinate" value={100} />
            </Box>
          {[1, 2, 3, 4].map((id) => (
            <Accordion key={id}>
              <AccordionSummary>
                <Box className="w-full">
                  <Box className="flex justify-between items-center">
                    <Box>
                    <h3 className="text-xl text-gray-800 pb-5">Machine {id}</h3>
                      <Box mb={1}>
                        <Typography variant="body2"><strong>Device ID:</strong>{machineData[id].device_id || 'Loading...'}</Typography>
                      </Box>
                      <Box mb={1}>
                        <Typography variant="body2"><strong>Temperature:</strong>{machineData[id].temperature || 'Loading...'}°C</Typography>
                      </Box>
                      <Box mb={1}>
                        <Typography variant="body2"><strong>Timestamp:</strong>{machineData[id].timestamp || 'Loading...'}</Typography>
                      </Box>
                    </Box>
                  </Box>
                </Box>
              </AccordionSummary>
            </Accordion>
          ))}
        </Card>
      </Box>
      </div>

    );
};

export default Dashboard;
