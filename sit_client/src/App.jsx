import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./Components/Login.jsx";
import './index.css';
import Dashboard from "./Components/Dashboard.jsx";
import 'react-toastify/dist/ReactToastify.css';


function App() {

  return (
      <Router>
        <Routes>
            <Route index element={<Login />} />
            <Route path='home' element={<Dashboard />} />
        </Routes>
      </Router>
  );
}

export default App;
