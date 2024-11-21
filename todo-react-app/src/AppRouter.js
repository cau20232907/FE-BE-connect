import React from "react";
import "./index.css"
import App from "./App";
import Login from "./Login";
import {BrowserRouter,Routes,Route} from "react-router-dom";
import { Box } from "@mui/material";
import SignUp from "./SignUp";

function CopyRight() {
    return (
        <p>copyright</p>
    );
}

function AppRouter() {
    return (
        <div>
            <BrowserRouter>
                <Routes>
                <Route path="/" element={<App />} />
                <Route path="login" element={<Login />} />
                <Route path="signup" element={<SignUp />} />
                </Routes>
            </BrowserRouter>
            <Box mt={5}>
                <CopyRight/>
            </Box>
        </div>
    )
}

export default AppRouter;