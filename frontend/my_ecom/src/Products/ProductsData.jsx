import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

export const ProductsData = () => {
    const location = useLocation();
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);

        let url = `${import.meta.env.VITE_API_URL}/All`;
        console.log("url" + url);
        if (location.pathname === "/products/Mens") {
            url = `${import.meta.env.VITE_API_URL}/Mens`;
        } else if (location.pathname === "/products/Womens") {
            url = `${import.meta.env.VITE_API_URL}/Womens`;
        }

        fetch(url)
            .then(res => res.json())
            .then(data => {
                setProducts(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });

    }, [location.pathname]);

    return { products, loading };
};
