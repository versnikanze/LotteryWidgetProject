<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <title>Lottery widget</title>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/javascript/website_functionality.js"></script>

    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="container">
    <div class="container-countdown">
        <h1 class="countdown-message">New winner in <span id="time">30</span> s</h1>
    </div>
    <div class="container-data">
        <div class="container-winner-list">
            <div class="container-winner-inner">
                <div class="wrap-table100">
                    <div class="table100 ver1 m-b-110">
                        <div class="table100-head">
                            <table>
                                <thead>
                                <tr class="row100 head">
                                    <th class="cell100 column1">Winners</th>
                                    <th class="cell100 column2">Number</th>
                                </tr>
                                </thead>
                            </table>
                        </div>

                        <div id="data_container" class="table100-body js-pscroll">
                            <table>
                                <tbody>
                                <tr class="row100 body">
                                    <td class="cell100 column1">e</td>
                                    <td class="cell100 column2">5</td>
                                </tr>

                                <tr class="row100 body">
                                    <td class="cell100 column1">d</td>
                                    <td class="cell100 column2">4</td>
                                </tr>

                                <tr class="row100 body">
                                    <td class="cell100 column1">v</td>
                                    <td class="cell100 column2">3</td>
                                </tr>

                                <tr class="row100 body">
                                    <td class="cell100 column1">b</td>
                                    <td class="cell100 column2">2</td>
                                </tr>

                                <tr class="row100 body">
                                    <td class="cell100 column1">a</td>
                                    <td class="cell100 column2">1</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>


                </div>
            </div>
        </div>
        <div class="container-submit">
            <div class="container-submit-inner">
                <div class="input-container">
                    <div class="container-name">
                        <input id="input_name" type="text" placeholder="Contestant name">
                    </div>
                    <div class="container-number">
                        <input id="input_picked_number" type="text" placeholder="Picked number">
                    </div>
                </div>
                <div id="contestant-registered" class="no-display-message information-message">Contestant registered
                </div>
                <div class="submit-container"><a class="css-button" onclick="sendContestantPick()"> <span
                        class="css-button-icon">
        <svg width="16" height="16" viewBox="2 2 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd"
                d="M13.293 3.293a1 1 0 011.414 0l2 2a1 1 0 010 1.414l-9 9a1 1 0 01-.39.242l-3 1a1 1 0 01-1.266-1.265l1-3a1 1 0 01.242-.391l9-9zM14 4l2 2-9 9-3 1 1-3 9-9z"
                clip-rule="evenodd"/>
          <path fill-rule="evenodd"
                d="M14.146 8.354l-2.5-2.5.708-.708 2.5 2.5-.708.708zM5 12v.5a.5.5 0 00.5.5H6v.5a.5.5 0 00.5.5H7v.5a.5.5 0 00.5.5H8v-1.5a.5.5 0 00-.5-.5H7v-.5a.5.5 0 00-.5-.5H5z"
                clip-rule="evenodd"/>
        </svg>
        </span> <span class="css-button-text">Submit</span> </a></div>
            </div>
        </div>
    </div>
</div>
</body>

</html>