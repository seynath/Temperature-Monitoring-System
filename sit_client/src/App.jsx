import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./Components/Login.jsx";
import './index.css';

function App() {

  return (
      <Router>
        <Routes>
            <Route index element={<Login />} />
        </Routes>
      </Router>
  );
}

export default App;
