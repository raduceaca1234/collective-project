import React, { useState } from 'react'
import styles from '../../styles/contact.module.scss'



const Contact = (props) =>{
    const [formData,setFormData] = useState({})
    const data=[
        {
            name: 'David Morgan',
            statistics: 'David has a response rate of 98%. Hist clients got a response in about 10 minutes and they were 97% satisfied with the response',
            phone_number: '0723455677',
            email: 'david@gmail.com',
            photo:"https://s1.cel.ro/images/mari/chitara-clasica-wood-style-96-cm-lemn-dx-s-dielisi.jpg"
        }
    ]

    const updateInput = e =>{
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        })
        
    }

    const handleSubmit = event =>{
        event.preventDefault()
        setFormData({
            name:'',
            phone_number: '',
            interval:'',
            message:'',
        })
        console.log(formData) 
    }

    const getContactInfo =()=>{
        data=[
            {
                name: 'David',
                statistics: 'Dave has a response rate of 98%. Hist clients got a response in about 10 minutes and they were 97% satisfied with the response',
                phone_number: '0723455677',
                email: 'david@gmail.com',
                photo:"https://s1.cel.ro/images/mari/chitara-clasica-wood-style-96-cm-lemn-dx-s-dielisi.jpg"
            }
        ]
    }

    return(
        <>
            <h1 className={styles.h1} ref={props.headref}>Contact</h1>
            <div  className={styles.main_div}>
                <form onSubmit={handleSubmit} className={styles.new_info}>
                    <p className={styles.text}>Your name</p>
                    <input className={styles.input}
                    type="text"
                    name="name"
                    placeholder="Enter name"
                    onChange={updateInput}
                    value={formData.name || ''}
                    />

                    <p className={styles.text}>Contact phone</p>
                    <input className={styles.input}
                    type="text"
                    name="phone_number"
                    placeholder="+40"
                    onChange={updateInput}
                    value={formData.phone_number}
                    />

                    <p className={styles.text}>Demanded interval</p>
                    <input className={styles.input}
                    type="text"
                    name="interval"
                    placeholder="dd-mm-yy"
                    onChange={updateInput}
                    value={formData.interval}
                    />

                    <p className={styles.text}>Message</p>
                    <input className={styles.message}
                    type="text"
                    name="message"
                    onChange={updateInput}
                    value={formData.message || ' '}
                    />
                    <div className={styles.buttons}>
                        <button type="submit" className={styles.sendButton}>Send</button>
                        <button className={styles.resetButton}>Reset</button>
                    </div>

                </form>
                <div className={styles.contact_info}>
                    <p style={{fontSize: 20}}>Writing to</p>
                    
                    <img className={styles.profile_img} src={data[0].photo}/>
                    
                    <p className={styles.name}><b>{data[0].name}</b></p>

                    <p className={styles.statistics}>{data[0].statistics}</p>

                    <p style={{fontSize: 15}}><b>You can also get in touch with {data[0].name} via</b></p>
                    <p style={{color:"blue"}}>{data[0].email}</p>

                <p style={{fontSize: 15}}><b>Phone number:</b> {data[0].phone_number}</p>
                    

                    

                </div>

            </div>
            
            

        </>
    )
}
export default Contact