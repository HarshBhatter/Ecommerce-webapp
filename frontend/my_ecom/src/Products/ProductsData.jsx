import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

export const ProductsData = () => {
    const location = useLocation();
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);

        let url = "http://localhost:8080/All";

        if (location.pathname === "/products/Mens") {
            url = "http://localhost:8080/Mens";
        } else if (location.pathname === "/products/Womens") {
            url = "http://localhost:8080/Womens";
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
