import React from 'react'
import './AddProduct.css'
import { useState } from 'react';
import { FaRupeeSign } from "react-icons/fa";

export const AddProduct = () => {
    const [image, setImage] = useState(null)
    const [sizes, setSizes] = useState([])
    const [size, setSize] = useState("")
    const [quantity, setQuantity] = useState("")

    // ➕ Add size + quantity
    const addSize = () => {
        if (!size || !quantity) return alert("Size & quantity required")

        setSizes([...sizes, {
            size: parseInt(size),
            quantity: parseInt(quantity)
        }])

        setSize("")
        setQuantity("")
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        if (sizes.length === 0) {
            alert("Add at least one size")
            return
        }

        const form = e.target

        const addProductRequest = {
            name: form.name.value,
            Type: form.type.value,
            fit: form.fit.value,
            price: parseFloat(form.price.value),
            description: form.description.value,
            gender: form.gender.value,
            color: form.color.value,
            addProductSizeRequest: sizes   // ✅ ARRAY
        }

        const formData = new FormData()
        formData.append("addProductRequest", new Blob([JSON.stringify(addProductRequest)],
            {
                type: "application/json"
            })
        )
        formData.append("image", image)

        try {
            const res = await fetch("http://localhost:8080/AddProducts", {
                method: "POST",
                // headers: {
                //   Authorization: `Bearer ${localStorage.getItem("token")}`
                // },
                body: formData
            })

            if (!res.ok) throw new Error("Failed")

            alert("Product added successfully")
            setSizes([])
            form.reset()
        }
        catch (err) {
            console.error(err)
            alert("Error adding product")
        }
    }
    return (
        <div className='Add-product'>
            <form onSubmit={handleSubmit}>
                <div className='box'>
                    <div className='left'>
                        <label htmlFor='Name'>Product Name</label>
                        <input type="text" id="" name="name" required />

                        <label htmlFor='Description'>Description</label>
                        <input type="text" id="" name="description" />

                        <label htmlFor='Fit'>Fit</label>
                        <input type="text" id="" name="fit" />

                        <label htmlFor='Type'>Type</label>
                        <input type="text" id="" name="type" />

                        <label htmlFor='Price'>Price</label>
                        <input type="number" id="" name="price" />
                    </div>
                    <div className='right'>
                        <label htmlFor='Gender'>Gender</label>
                        <select id="" name="gender" required>
                            <option value="Men">Mens</option>
                            <option value="Women">Womens</option>
                        </select>
                        <label htmlFor='Color'>Color</label>
                        <input type="text" id="" name="color" required />
                        <div className='size-quantity'>
                            <div className='left'>
                                <label htmlFor='Size'>Size</label>
                                <select value={size} onChange={(e) => setSize(e.target.value)}>
                                    <option value="">select size</option>
                                    <option value="36">36</option>
                                    <option value="38">38</option>
                                    <option value="40">40</option>
                                    <option value="42">42</option>
                                    <option value="44">44</option>
                                </select>
                            </div>
                            <div className='right'>
                                <label htmlFor='Quantity'>Quantity</label>
                                <input type="number" value={quantity} onChange={(e) => setQuantity(e.target.value)}/>
                            </div>
                        </div>
                        <ul>
                        {sizes.map((s, i) => (
                            <li key={i}>Size {s.size} → Qty {s.quantity}</li>
                        ))}
                        </ul>
                        <button type="button" onClick={addSize}>Add</button>

                        <label htmlFor='Picture'>Picture</label>
                        <input type="file" onChange={(e) => setImage(e.target.files[0])} />
                    </div>
                </div>
                <button type='submit'>Add Product</button>
            </form>
        </div>
    )
}
