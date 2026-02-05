import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

export const ProductsData = (page) => {
    const location = useLocation();
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [totalpages, setTotalpages] = useState(0);

    useEffect(() => {
        setLoading(true);

        let url = `${import.meta.env.VITE_API_URL}/All?page=${page}`;
        console.log("url" + url);
        if (location.pathname === "/products/Mens") {
            url = `${import.meta.env.VITE_API_URL}/Mens?page=${page}`;
        } else if (location.pathname === "/products/Womens") {
            url = `${import.meta.env.VITE_API_URL}/Womens?page=${page}`;
        }

        fetch(url)
            .then(res => res.json())
            .then(data => {
                setProducts(data.content);
                setLoading(false);
                setTotalpages(data.totalPages);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });

    }, [location.pathname, page]);

    return { products, loading, totalpages };
};
