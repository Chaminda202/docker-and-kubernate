const express = require('express')
const app = express()
const port = 3000

/* app.get('/', (req, res) => {
  res.send('Hello World...!!!')
}) */

app.get('/', (req, res) => res.json([
    {
        name: 'Test user',
        email: 'test@gmail.com'
    },
    {
        name: 'Abd user',
        email: 'abc@gmail.com'
    },
    {
        name: 'Sample user',
        email: 'sample@yahoo.com'
    }
]))

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})