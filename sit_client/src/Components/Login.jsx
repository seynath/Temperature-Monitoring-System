import { useState } from 'react';
import { ValidateUser } from '../ApiService/api.jsx';
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from 'react-router-dom';
import 'react-toastify/dist/ReactToastify.css';

const Login = () => {
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const nav = useNavigate();

  const login = async (e) => {
    e.preventDefault();
    if (!username || !password) {
      toast.error('Please fill all fields', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: 'light',
      });
      return;
    }
    const data = { username, password };

    try {
      const response = await ValidateUser(data);
      console.log(response);
      if (response?.data?.code === '00') {
        sessionStorage.setItem('token', response.data.token);
        nav('/home');
      } else {
        toast.error('Invalid User', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: 'light',
        });
      }
    } catch (error) {
      toast.error('An error occurred. Please try again.', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: 'light',
      });
    }
  };

  return (
      <div className="flex items-center justify-center min-h-screen bg-gray-100">
        <div className="w-full max-w-xs">
          <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
            <h2 className="mb-6 text-center text-2xl font-bold text-gray-700">Login</h2>
            <div className="mb-4">
              <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="username">
                Username
              </label>
              <input
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="username"
                  type="text"
                  placeholder="Username"
                  onChange={(e) => { setUserName(e.target.value); }}
              />
            </div>
            <div className="mb-6">
              <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
                Password
              </label>
              <input
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
                  id="password"
                  type="password"
                  placeholder="********"
                  onChange={(e) => { setPassword(e.target.value); }}
              />
            </div>
            <div className="flex items-center h-full justify-between">
              <button
                  className="bg-blue-500 hover:bg-blue-700 text-white h-[35px] font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                  type="button"
                  onClick={login}
              >
                <p className="text-sm">Sign In</p>
              </button>
              <a
                  className="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800"
                  href="#"
              >
                Forgot Password?
              </a>
            </div>
          </form>
          <p className="text-center text-gray-500 text-xs">
            &copy;2024 Acme Corp. All rights reserved.
          </p>
        </div>
        <ToastContainer />
      </div>
  );
};

export default Login;
