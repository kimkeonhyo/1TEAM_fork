import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import MainPage from './pages/MainPage';
import SearchResultPage from './pages/SearchResultPage';
import StockPage from './pages/StockPage';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/search" element={<SearchResultPage />} />
        <Route path="/stock/:stockId" element={<StockPage />} />
      </Routes>
    </Router>
  );
};

export default App;
