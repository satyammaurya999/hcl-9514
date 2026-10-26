import React, { useEffect, useState } from 'react';

function UseFetchApi() {

    const [serverData, setServerData] = useState([]);

    useEffect(() => {

        async function getData() {

            try {

                const response = await fetch(
                    "http://localhost:8080/employee/getall"
                );

                const jsonData = await response.json();

                console.log(jsonData);

                setServerData(jsonData);

            } catch (e) {

                console.log(e);

            }
        }

        getData();

    }, []);

    return (
        <div>

            <h2>Employee Data</h2>

            <h3>{JSON.stringify(serverData)}</h3>

        </div>
    );
}

export default UseFetchApi;