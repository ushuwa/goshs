document.addEventListener('DOMContentLoaded', async () => {
  const form = document.getElementById('questionnaireForm');
  const resultDiv = document.getElementById('result');
  const resultList = document.getElementById('recommendationList');

  const response = await fetch('/api/questionnaires');
  const questions = await response.json();

  questions.forEach(q => {
    const div = document.createElement('div');
    div.innerHTML = `
      <p>${q.text}</p>
	  ${['Strongly Disagree', 'Disagree', 'Neutral', 'Agree', 'Strongly Agree']
	    .map((label, i) => `
	      <label>
	        <input type="radio" name="${q.key}" value="${i}" />
	        ${label}
	      </label>
	    `).join('')}
    `;
    form.appendChild(div);
  });

  document.getElementById('submitBtn').addEventListener('click', async (e) => {
    e.preventDefault();
    const formData = new FormData(form);
    const payload = {};
    for (const [k,v] of formData.entries()) payload[k] = parseInt(v);

    const res = await fetch('/api/strand/recommend', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(payload)
    });

    const data = await res.json();
    resultList.innerHTML = '';
    for (const strand in data) {
      const div = document.createElement('div');
      div.textContent = `${strand}: ${data[strand]}`;
      resultList.appendChild(div);
    }
    resultDiv.style.display = 'block';
  });
});
