import React from 'react'
import { useState } from 'react'  
import { useEffect } from 'react'


export const IndividualProductData = (id) => {
  const [data, setData] = useState([])
  useEffect(() => {
    if (id) {
      fetch(`${import.meta.env.VITE_API_URL}/All/?id=${id}`)
        .then(res => res.json())
        .then(json => setData(json))
    }
  }, [id])
  return data;
}
